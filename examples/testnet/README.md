# Base Sepolia 走查（测试网示例）

目标：用一个测试网地址，把「开单 → 链上转账 → 确认 → 回调」这四步跑通，全程不碰真钱。

## 0. 准备

- Base Sepolia 测试 ETH（官方 faucet 领，几分钟到账）
- Base Sepolia USDC：`0x036CbD53842c5426634e7929541eC2318f3dCF7e`（chainId `84532`）
- 一个你自己掌控私钥的钱包（不要用交易所地址）

## 1. 起自己的网关

```bash
export RPC_BASE_SEPOLIA=https://sepolia.base.org
export API_KEY=local-test-key
export WEBHOOK_SECRET=local-webhook-secret
node src/index.js        # 网关实现不在本仓库，此处指你本地部署的那份
```

## 2. 开一张单

```bash
curl -s -X POST http://127.0.0.1:8787/api/invoices \
  -H "content-type: application/json" \
  -H "x-api-key: local-test-key" \
  -d '{"chainKey":"base-sepolia","symbol":"USDC","amount":"1.00","orderId":"test-1001"}'
```

拿返回里的 `depositAddress` 与 `invoiceId`。

## 3. 从钱包转账

- 网络：Base Sepolia
- 资产：USDC
- 收款地址：上一步的 `depositAddress`
- 金额：1.00（多一点少一点都不行，超付/欠付会被标成 `overpaid` / `underpaid`）

## 4. 看状态推进

```bash
curl -s http://127.0.0.1:8787/api/invoice/<invoiceId> -H "x-api-key: local-test-key"
```

预期：`pending` → `confirming` → `confirmed`（Base Sepolia 用 12 个确认）。

## 5. 核对回调

商户端收到的 POST 应带 `x-aegis-timestamp` 与 `x-aegis-signature`，用 `sdk/` 里的 `verifyWebhook` 校验；签名对不上就丢掉，别当成功。

## 常见卡点

| 现象 | 原因 |
| --- | --- |
| 一直 `pending` | 转错链或转错地址；或 RPC 未配置，网关根本没扫 |
| `confirming` 不前进 | 测试网出块慢，等；或 RPC 限流 |
| 回调 401 / 签名失败 | 用了重新序列化的 body，或 secret 不一致 |
| `overpaid` | 金额比单子多，结账时按实际到账处理，别按单子金额 |

本走查只验证**测试网行为**，不构成安全审计。
