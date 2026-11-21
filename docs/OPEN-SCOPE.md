# What is public in this repository — and what is not

This repository exists so that merchants, reviewers and regulators can understand what Aegis Chain does, how it
is bounded, and how to report problems — **without** exposing the implementation.

## Published (openly readable, CC BY 4.0)

| Area | Content |
| --- | --- |
| Product documentation | Multilingual README set (zh / en / fr / ko / ja / ru / id / es / ar), features, mobile usage, roadmap |
| Architecture summary | Components, data flow, invoice state machine, cross-chain intent states, trust boundaries |
| Compliance notes | Non-custodial boundary, MiCA reasoning, GDPR handling, sanctions screening policy |
| Security policy | Disclosure channel, response targets, known limitations |
| Release notes | Version, verified chains, test counts |

## Withheld (proprietary)

| Area | Why |
| --- | --- |
| `PaymentHub` contract source and deployment scripts | Contract bytecode and constructor parameters are the settlement security surface |
| Payment engine, RPC failover, scanner internals | Address-pool allocation and finality heuristics are the anti-double-credit surface |
| Cross-chain reconciler implementation | Reconciliation rules encode the anti-loss logic |
| Signing keys, API keys, production endpoints, address pools | Operational secrets |
| Merchant configurations and real transaction data | Third-party data |

## Why partially closed

1. **Loss prevention.** The scanner/finality logic decides when a payment counts as settled. Public code invites
   crafted reorg and double-credit attacks against live merchants faster than they can be patched.
2. **Third-party safety.** Address pools and merchant data cannot be published; the engine is inseparable from them.
3. **Individual developer, no legal shield.** With no company, a public bug tracker on the engine would become a
   map for attackers with nobody to absorb the consequences.

## What can still be verified without the source

- Chain-level behaviour: `PaymentHub` deployments are public on Base / Base Sepolia and can be inspected on-chain.
- Documented behaviour can be reproduced against the public demo gateway.
- The three Android builds (dev / test / prod) share one feature set; the parity check is described in `docs/FEATURES.md`.

## If you need more

Open an issue describing what you need to verify and why (audit, regulatory review, integration). Scope-limited
read access can be arranged case by case; it is not granted for forks or resale.
