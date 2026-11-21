# Aegis Chain

> A non-custodial, multi-chain payment console for merchants who want to accept crypto without handing anyone their keys.
> 非托管多链收款平台：商户自己掌握私钥，平台只做收款、对账与跨链意图台账。

[中文](docs/i18n/README.zh.md) · [English](docs/i18n/README.en.md) · [Français](docs/i18n/README.fr.md) · [한국어](docs/i18n/README.ko.md) · [日本語](docs/i18n/README.ja.md) · [Русский](docs/i18n/README.ru.md) · [Bahasa Indonesia](docs/i18n/README.id.md) · [Español](docs/i18n/README.es.md) · [العربية](docs/i18n/README.ar.md)

---

## What it is

A self-hosted payment gateway plus a mobile console. Merchants create an invoice, the customer pays on-chain, the gateway watches the chain and settles the invoice. No accounts, no custody, no token.

- **Multi-chain receiving** — Ethereum, Base, Arbitrum, Polygon, BNB Chain, plus BTC and TRON watching via public indexers
- **On-chain settlement** — a `PaymentHub` contract releases funds only to the merchant's own address with a signed, replay-proof settlement proof
- **Cross-chain intents** — a ledger with explicit states (source / confirm / credit / done); no automatic retries, every hop is reconciled
- **Safety rails** — sanctions screening before an invoice is issued, RPC failover with circuit breaking, reorg-aware confirmations
- **Mobile app** — native Android shell (dev / test / prod builds); iOS installs as PWA
- **i18n** — product UI in 5 languages (zh, en, de, fr, es), this repository's docs in 9

## What is public here

| Open | Closed |
| --- | --- |
| Documentation, architecture summary, compliance notes, security policy, roadmap | Payment engine source, chain scanners, settlement contract source and deployment scripts |
| Issue tracker, translation corrections, bug reports on documented behaviour | Signing keys, production endpoints, address pools, merchant configurations |

This repository is a public information and support surface. The core implementation is **not** open source; see [docs/OPEN-SCOPE.md](docs/OPEN-SCOPE.md).

## Compliance boundary

Non-custodial software only: no custody, no fiat on/off-ramp, no token issuance, no self-built bridge. Under EU MiCA this is software provision rather than a CASP service; the moment custody, exchange or fiat conversion is added, a CASP licence is required and an individual cannot hold one. See [docs/COMPLIANCE.md](docs/COMPLIANCE.md).

## Docs

- [Architecture](docs/ARCHITECTURE.md) · [Features](docs/FEATURES.md) · [Compliance](docs/COMPLIANCE.md) · [Roadmap](docs/ROADMAP.md) · [Security](SECURITY.md) · [Contributing](CONTRIBUTING.md)

## Status

Version 0.4.0 — Base mainnet and Base Sepolia verified against live chains; 29 unit tests and 11 end-to-end checks passing. Not audited, not production-hardened. Do not move real customer funds through it yet.
