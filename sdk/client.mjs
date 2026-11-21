/**
 * 商户侧最小客户端：开单 -> 轮询 -> 校验回调签名。
 * 用法：node sdk/client.mjs（需网关已启动，默认 8787）
 */
import crypto from 'node:crypto';

const BASE = process.env.GATEWAY || 'http://127.0.0.1:8787';
const KEY = process.env.API_KEY || '';

async function call(method, path, body) {
  const res = await fetch(BASE + path, {
    method,
    headers: { 'content-type': 'application/json', ...(KEY ? { 'x-api-key': KEY } : {}) },
    body: body ? JSON.stringify(body) : undefined,
  });
  return { status: res.status, json: await res.json() };
}

/** 与网关一致的回调签名：HMAC-SHA256(secret, `${timestamp}.${rawBody}`) */
export function verifyWebhook({ secret, timestamp, rawBody, signature }) {
  const expected = crypto.createHmac('sha256', secret).update(`${timestamp}.${rawBody}`).digest('hex');
  const a = Buffer.from(expected);
  const b = Buffer.from(String(signature || ''));
  if (a.length !== b.length) return false;
  return crypto.timingSafeEqual(a, b);
}

const { json } = await call('POST', '/api/invoices', { chainKey: 'demo', symbol: 'USDC', amount: '25', orderId: 'sdk-demo-1' });
console.log('invoice', json.invoice?.id, json.invoice?.address, json.invoice?.status);

// demo 链可直接模拟到账；真实链这一步由付款人完成
if (json.invoice?.chainKey === 'demo') {
  await call('POST', '/api/demo/pay', { invoiceId: json.invoice.id });
  console.log('simulated deposit on demo chain');
}

for (let i = 0; i < 20; i++) {
  const st = await call('GET', `/api/invoice/${json.invoice.id}`);
  console.log(`poll ${i}`, st.json.invoice.status, `${st.json.invoice.confirmations}/${st.json.invoice.requiredConfirmations}`);
  if (['confirmed', 'expired'].includes(st.json.invoice.status)) break;
  await new Promise((r) => setTimeout(r, 1500));
}
