# Licensing map

| Path | License | What you may do |
| --- | --- | --- |
| `demo/` | MIT | Use, modify, redistribute, embed — even commercially, keep the notice |
| `sdk/` | MIT | Integrate into your own backend, fork it, ship it |
| `mobile-shell/` | MIT | Build your own branded shell from it |
| `examples/` | MIT | Copy the walkthroughs and sample payloads |
| `docs/` | CC BY 4.0 | Share and adapt with attribution to "Aegis Chain, github.com/ugjdygh/aegis-chain" |
| Core production implementation | **Not licensed, not distributed** | Nothing is granted. No source, no binaries, no rights |
| Name, logo, app icons | Trademark of the project owner | Do not use them to imply affiliation or endorsement |

## Why the core is closed

1. The scanner and finality logic decides when a payment counts as settled. Public source invites crafted reorg and double-credit attacks against live merchants faster than they can be patched.
2. Address pools and merchant data cannot be published; the engine is inseparable from them.
3. This is an individual project with no corporate legal shield. A public bug tracker on the engine would be a map for attackers with nobody to absorb the consequences.

## What you can still verify without the source

- Demo behaviour: `demo/index.html` reproduces the invoice state machine in memory.
- Interface contract: `sdk/client.mjs` shows the request/response shapes and the exact HMAC webhook scheme.
- On-chain behaviour: settlement deployments are public on Base and Base Sepolia and inspectable on a block explorer.
- Shell behaviour: `mobile-shell/` is the exact code in the released APKs.

Need more for an audit or a regulatory review? Open an issue stating what you must verify; scope-limited read access is decided case by case and is not granted for forks or resale.
