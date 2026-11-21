# Roadmap

Status as of 0.4.0: Base mainnet and Base Sepolia verified against live chains; 29 unit tests and 11 end-to-end
checks green; three Android builds verified for feature parity.

## Next

| Priority | Item | Why |
| --- | --- | --- |
| P0 | Independent review of `PaymentHub` and the finality logic | Only then should real customer funds be considered |
| P0 | HTTPS-only production profile, key rotation, log minimisation | GDPR and basic hygiene |
| P1 | External store adapter (Postgres) behind the existing interface | Concurrency beyond a single process |
| P1 | Merchant-side reconciliation export (CSV / accounting hooks) | Real bookkeeping need |
| P2 | More chain adapters behind the same interface | Extensibility without touching the engine |
| P2 | Translation coverage for product UI (ko, ja, ru, id, ar) | Docs are already 9 languages; UI is 5 |
| P3 | HarmonyOS NEXT client | Requires a separate ArkTS project; out of scope for now |

## Explicitly out of scope

Custody, fiat ramps, token issuance, self-built bridge, trading, lending, staking-as-a-service. These are the items
that would move the project into a licensing regime an individual cannot enter.

## How to influence it

Open an issue with the use case and the constraint behind it. Integration needs (a specific chain, a specific
accounting format) are the fastest to move.
