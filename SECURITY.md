# Security policy

## Reporting

Please report vulnerabilities through **GitHub Security Advisories** on this repository (Security → Report a
vulnerability). Do not open a public issue for anything exploitable.

Include: affected component, chain/network, reproduction steps, impact (funds at risk? merchant data? denial of
service?), and whether it is already public.

## Response targets

| Severity | First response | Fix target |
| --- | --- | --- |
| Critical (funds at risk, settlement bypass) | 48 h | 7 days, with merchant notice |
| High (double credit, forged webhook) | 72 h | 14 days |
| Medium / Low | 7 days | next release |

No bug bounty is offered. Credit is given in release notes unless you ask otherwise.

## What is in scope

- The documented API contract and its authentication
- Webhook signing and replay resistance
- Invoice state transitions and finality rules
- Publicly deployed `PaymentHub` contracts
- The Android shell (deep links, WebView configuration, local storage)

Out of scope: social engineering, physical access, third-party RPC providers, and anything requiring merchant
private keys.

## Hardening checklist for self-hosters

1. Never expose the gateway without HTTPS and an API key.
2. Keep `WEBHOOK_SECRET` and API keys out of the repository and out of shell history.
3. Restrict the deposit address pool to addresses you control the keys for — watch-only, never imported into the gateway.
4. Set per-chain confirmations conservatively; a fast settlement is not worth a reorg loss.
5. Back up the state file; losing it loses invoice history, not funds.
