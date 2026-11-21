# Features

Mapped to the eight requirements the platform was built against.

| # | Requirement | How it is delivered |
| --- | --- | --- |
| 1 | Accept multiple major digital currencies | EVM: Ethereum, Base, Arbitrum, Polygon, BNB Chain with native coin + USDC/USDT; BTC and TRON watched through public indexers. One watch-only address list serves all EVM chains |
| 2 | Smart contracts | `PaymentHub` settlement contract: non-custodial release to the merchant address, signed replay-proof settlement, no upgradeable proxy, no admin drain path |
| 3 | Cross-chain transactions | Intent ledger with explicit states (source / confirm / credit / done), per-hop evidence, reconciliation view; deliberately **no** automatic retry and no self-built bridge |
| 4 | Safety and stability | Sanctions screening before invoice issue; multi-RPC failover with circuit breaker; reorg-aware finality; HMAC webhooks with outbox retry; per-invoice address isolation |
| 5 | Easy to use | Merchant console (create invoice, live status, intents, metrics) and a customer checkout page that opens from a link or QR; Android app installs in one tap |
| 6 | Extensibility | Chains and tokens are declarative config entries; scanners are per-chain adapters; REST + webhook surface for any backend; SDK client for scripts |
| 7 | Community and ecosystem | This public repository (docs, issues, translations), open API contract, published demo, on-chain verifiable contract deployments |
| 8 | Internationalization | Product UI: zh, en, de, fr, es. Documentation: zh, en, fr, ko, ja, ru, id, es, ar (UN six official languages plus Korean, Japanese and Indonesian) |

## Three builds, one feature set

Dev, test and production Android builds load the same server code; they differ only in gateway URL, package
suffix and app label. Integrity rule: a shared feature manifest is the single source of truth, the three APKs must
contain an identical file-entry set and identical shared assets, and all three gateway instances must return an
identical feature list. Any divergence fails the check instead of shipping.

## Known limits

- Not security audited; no production hardening yet.
- Android only natively (iOS via PWA); HarmonyOS NEXT not supported (it cannot install APKs).
- BTC and TRON are watch-only: no signing, no sweeping from the app.
