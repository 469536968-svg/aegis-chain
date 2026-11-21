# sdk/ — 商户侧最小客户端（MIT）

用一个文件演示商户后端该怎么和网关打交道：

| 能力 | 函数 / 位置 |
| --- | --- |
| 创建收款单 | `POST /api/invoices` |
| 轮询单张收款单状态 | `GET /api/invoice/:id` |
| 校验回调签名 | `verifyWebhook({ secret, timestamp, rawBody, signature })` |

## 回调签名约定

```
signature = hex( HMAC-SHA256( secret, `${timestamp}.${rawBody}` ) )
```

校验时必须做三件事，缺一不可：

1. 用**原始请求体字符串**算签名，不要 `JSON.stringify(JSON.parse(body))`——键序会变
2. 比较用**常量时间**比较（`crypto.timingSafeEqual`），不要 `===`
3. 拒绝时间戳偏差超过 5 分钟的请求，防重放

## 跑起来

```bash
# 需要你先跑起自己的网关
export GATEWAY=http://127.0.0.1:8787
export API_KEY=你的网关 API Key       # 没设可留空
node sdk/client.mjs
```

## 注意

- 这个 SDK 只覆盖「读」与「校验」，**不含**签名转账、私钥管理、地址生成策略
- 网关端实现（含结算、对账、地址池）不在本仓库
