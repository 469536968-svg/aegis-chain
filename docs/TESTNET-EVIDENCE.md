# Testnet evidence — real payment loop

A single invoice completed the full `pending → confirming → confirmed` path on Base Sepolia with no mocks,
no seeded balances and no manual status override. The gateway recognised the payment by scanning blocks
itself; the state flipped only after the on-chain confirmation count reached the configured threshold.

| Field | Value |
|---|---|
| Invoice | `inv_a5c63ae8bf364624a94b` |
| Chain | Base Sepolia (84532) |
| Amount | 0.001 ETH (native) |
| Deposit address | `0xf53453c54c1ee7031af93d39c4171c6c5825baa5` |
| Payer address | `0x1009e923e8f8524359fe27139eb5cb6c45078c69` |
| Transaction | `0x12298999ce1968a5eeca5956dc69f0363884ac4bf9620aba9e5f23a3ffd0708f` |
| Block | 61,956,342 (status `success`) |
| Confirmations at check | 1182 (threshold 5) |
| Final state | `confirmed`, `received=0.001`, `overpaid=false`, `reorgedCount=0` |

Verify: <https://sepolia.basescan.org/tx/0x12298999ce1968a5eeca5956dc69f0363884ac4bf9620aba9e5f23a3ffd0708f>

Funding path, all publicly verifiable: a self-custody wallet received test ETH from a proof-of-work faucet and
sent 0.03 ETH to the payer's L1 Sepolia account; the payer then bridged 0.026 ETH to Base Sepolia through the
canonical `OptimismPortal` (`0x49f53e41452C74589E85cA1677426Ba426459e85`), deposit tx
`0x4dc2af185143cbe02652a89d970dcaa8a89a3f289a0f6df220b3194d212fc3e8` (L1 block 12,185,723). The minted
balance arrived on L2, the payer sent 0.001 ETH to the invoice address, and the scanner counted 5
confirmations before the invoice left `confirming`.

## Why this took longer than it should

Three real defects surfaced, all of them worth fixing rather than working around:

1. **A portal deposit with no `value` override.** Passing the amount as a function argument does not attach
   ETH to the transaction. The receipt looked successful and the `TransactionDeposited` event even carried
   `value=0.026`, but the L1 balance never moved and nothing was minted on L2. Rule adopted: a bridge deposit
   is only considered done when the source-chain balance actually decreases.
2. **One unhealthy chain RPC blocking the whole scan loop.** The scanner loop was sequential across chains;
   Base mainnet's public endpoints were failing and burning retries and back-off, so the Base Sepolia
   scanner was never scheduled (`rpc.calls = 0` in metrics while the invoice stayed `pending`).
3. **A multi-thousand-block cursor gap scanned one block at a time.** Catching up took ~0.4s per block
   sequentially. Block fetching is now parallel during catch-up (bounded worker pool, with ordering preserved
   for cursor, fork hashes and payment matching), and token log queries are skipped unless an unfinished
   invoice actually needs that token. Measured on 60 blocks against the same endpoint: 24.2s serial → 5.1s
   at concurrency 8.

All 36 unit tests still pass after these changes.

## Still open

- Overpaid and underpaid counter-examples (must never reach `confirmed`)
- `PaymentHub` contract deployment stays behind the audit gate
- Per-chain scheduling in the gateway, so one failing chain can never starve another
