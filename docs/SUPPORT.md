# Support the project (donation page) / 支持项目

A public page at `/support` (alias `/donate`) where people can back the project. Same shape as the Ghost Downloader support page: pick a method, scan, done — plus a safety note.

## What it does

| Capability | Implementation |
| --- | --- |
| Methods | Configured through `DONATE_ADDRESSES`: EVM chains (Ethereum / Base / Arbitrum / Polygon / BNB), BTC, TRON, each with an asset label |
| QR code | Generated locally in the browser (bundled MIT-licensed qrcode-generator). **Addresses are never sent to a third-party QR service** |
| Wallet deep links | EIP-681 `ethereum:<addr>@<chainId>` for EVM, BIP-21 `bitcoin:<addr>`, `tron:<addr>` |
| Amount | Open amount, no fixed invoice — a donation does not need the settlement contract and must not enter merchant reconciliation |
| Languages | Five, matching the product UI (zh / en / de / fr / es) |
| Safety copy | Explicit: donate only through this page; anyone asking for a private key or seed phrase is a scam |

## The supporter list is off by default

The list is an **isolated data source**: it is not bound to on-chain arrivals, needs manual upkeep, and once shown it invites disputes about amount, date and anonymity. So the capability exists but stays out of the main flow:

- Without `DONATE_LIST=on` and `DONATE_RECORDS_FILE`, the card is never rendered and `GET /api/support/records` returns 404.
- When enabled, entries are sanitised before reaching the page: control characters and angle brackets stripped, name ≤24 chars, message ≤80 chars, amount must be a number in 0–1e9, date must be `YYYY-MM-DD`; anything invalid is dropped whole.

The plumbing is ready; turn it on when on-chain accounting can feed it automatically.

## Configuration

```bash
export DONATE_ADDRESSES='[{"chain":"base","asset":"USDC","address":"0x..."}]'
# optional: export DONATE_LIST=on DONATE_RECORDS_FILE=./data/supporters.json
node src/index.js
```

Address formats are validated server-side (EVM: `0x` + 40 hex; BTC: bech32 or 1/3 prefix; TRON: T + 33 base58). One bad entry makes the page report a configuration error and shows no address at all — better "not configured" than a typo that loses money.

## Compliance

Donations go **straight to the project address**; the gateway never holds, forwards or refunds them, and the page says on-chain transfers are final. No return is promised, no token is issued, no presale — which keeps it outside securities and MiCA service definitions. Adding donor NFTs or perks would cross that line; do not.

## Tests

`test/support.test.js` — 7 cases: wallet URI formats, demo fallback when unconfigured, valid configuration, invalid address rejected, list disabled by default, list sanitisation (XSS / bad date / negative amount / over-long message), empty record rejected.
