# Aegis Chain (English)

> A non-custodial, multi-chain payment console: keys stay with the merchant, the platform only handles invoices, reconciliation and the cross-chain intent ledger.

[Index](../../README.md) · [中文](README.zh.md) · [Français](README.fr.md) · [한국어](README.ko.md) · [日本語](README.ja.md) · [Русский](README.ru.md) · [Bahasa Indonesia](README.id.md) · [Español](README.es.md) · [العربية](README.ar.md)

## What it is

A self-hosted payment gateway plus a mobile console. A merchant opens an invoice, the customer pays on-chain, the gateway watches the chain and settles the invoice. No accounts, no custody, no token.

- **Multi-chain receiving** — Ethereum, Base, Arbitrum, Polygon, BNB Chain, plus BTC and TRON watching through public indexers
- **On-chain settlement** — the `PaymentHub` contract releases funds only to the merchant's own address, with a signed replay-proof settlement proof
- **Cross-chain intents** — a ledger with explicit states (source / confirm / credit / done); no automatic retry, every hop is reconciled
- **Safety rails** — sanctions screening before an invoice exists, multi-RPC failover with circuit breaking, reorg-aware finality
- **Mobile app** — native Android shell in three builds (dev / test / prod); iOS installs as a PWA
- **i18n** — product UI in five languages (zh, en, de, fr, es); this repository's docs in nine

## What is public here

| Open | Closed |
| --- | --- |
| Documentation, architecture summary, compliance notes, security policy, roadmap | Payment engine source, chain scanners, settlement contract source and deployment scripts |
| Issue tracker, translation fixes, bug reports on documented behaviour | Signing keys, production endpoints, address pools, merchant configuration |

The repository is the public information and support surface. The core implementation is **not** open source — see [OPEN-SCOPE.md](../OPEN-SCOPE.md).

## Compliance boundary

Non-custodial software only: no custody, no fiat on/off-ramp, no token, no self-built bridge. Under EU MiCA this is software provision, not a CASP service; adding custody, exchange or fiat conversion would require a CASP licence that an individual cannot obtain. See [COMPLIANCE.md](../COMPLIANCE.md).

## Documents

[Architecture](../ARCHITECTURE.md) · [Features](../FEATURES.md) · [Compliance](../COMPLIANCE.md) · [Roadmap](../ROADMAP.md) · [Security](../../SECURITY.md) · [Contributing](../../CONTRIBUTING.md)

## Status

Version 0.4.0 — Base mainnet and Base Sepolia verified against live chains; 29 unit tests and 11 end-to-end checks passing. Not audited and not production-hardened; do not route real customer funds through it yet.
