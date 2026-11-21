# Architecture (public summary)

The implementation is closed (see `OPEN-SCOPE.md`); this page describes only the shape, the states and the trust
boundaries, which are what an integrator or a reviewer needs.

```
 customer wallet                gateway (self-hosted)                 merchant
 ───────────────                ─────────────────────                 ────────
 pay on chain  ──────────▶  chain scanners (EVM ×5, BTC, TRON)
                                   │  detect transfer to deposit address
                                   ▼
                            invoice state machine
                            pending → detected → confirmed → settled | expired | overpaid | underpaid
                                   │
                                   ▼
                            settlement proof (signed, replay-proof)
                                   │
                                   ▼
                            PaymentHub contract  ──releases funds──▶  merchant address
                                   │
                            cross-chain intent ledger
                            source → confirm → credit → done   (manual/verified hops, no auto-retry)
                                   │
                                   ▼
                            webhooks (HMAC) + mobile console + REST API
```

## Components

| Component | Responsibility |
| --- | --- |
| Chain scanners | Watch deposit addresses per chain; EVM via JSON-RPC logs, with calldata fallback when a public RPC disables `eth_getLogs`; BTC via Esplora; TRON via TronGrid |
| Invoice state machine | One invoice, one deposit address, explicit terminal states; no silent mutation |
| Settlement contract | Holds nothing; releases only to the merchant address against a signed proof |
| Cross-chain ledger | Records intent and each hop; states advance only on evidence |
| Sanctions screening | Screens deposit addresses before an invoice is issued |
| Webhook outbox | HMAC-signed delivery with retry, so a merchant backend never misses a settled invoice |
| Console / checkout | Same API, five UI languages, works as PWA and inside the Android shell |

## Trust boundaries

1. **Keys** — never generated, stored or used by the platform. Merchants sign their own transactions.
2. **Funds** — the platform never holds a balance. Deposit addresses are watch-only.
3. **Finality** — confirmation thresholds are per chain and reorg-aware; a payment is not "settled" on first sight.
4. **Cross-chain** — no bridge contract. Hops are recorded and reconciled; failures are isolated, never retried blindly.

## Deployment shapes

- Single process, zero npm dependencies at runtime: `node src/index.js` on a laptop, a VPS, or inside Termux on a phone.
- State in a JSON file by default; swap for an external store when concurrency grows.
- Public exposure requires HTTPS and an API key; the Android shell allows cleartext only for local/LAN self-hosting.
