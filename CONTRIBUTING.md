# Contributing

Thanks for helping. This repository is **partially open**: the demo front end, SDK, Android shell, examples and docs are MIT-licensed and welcome contributions. The production engine is not here and will not be accepted as a patch.

## What we accept

| Area | Directory | Examples |
| --- | --- | --- |
| Demo front end | `demo/` | Mobile layout, invoice flow clarity, language switching, accessibility, a wallet deep-link example |
| SDK | `sdk/` | Examples for Python / PHP / Go, type definitions, webhook verification edge cases, better error messages |
| Android shell | `mobile-shell/` | WebView issues, offline page copy, build script portability, safe-area handling |
| Examples | `examples/` | Base Sepolia walkthrough corrections, sample payloads, screenshots |
| Docs and translation | `docs/` | Factual fixes, broken steps, Korean / Japanese / Russian / Indonesian / Arabic wording |

## What we do not accept

- The production gateway, chain scanners, reconciliation, `PaymentHub` contract, address pools or key handling — those live elsewhere and are proprietary.
- Requests to add custody, fiat ramps, token issuance, a bridge, or anything that moves the project into a licensing regime.
- Re-hosting the docs under the project name or logo (see `LICENSES.md`).
- Anything containing a private key, seed phrase, API key, real merchant address or customer data.
- Bulk reformatting or dependency additions without a prior issue.

## Workflow

1. Find or open an issue. Labels `good first issue`, `i18n`, `docs`, `android`, `testnet` tell you the scope and size.
2. Comment to claim it, so two people do not build the same patch.
3. Fork, branch from `main`, keep the change to one topic.
4. Verify before pushing:
   - `demo/`: open `demo/index.html` directly, click through create invoice → simulate deposit → confirmed, and switch every language.
   - `sdk/`: `node sdk/client.mjs` against a gateway you run yourself; webhook verification still matches the documented HMAC scheme.
   - `mobile-shell/`: `./build.sh dev` produces an installable APK; the offline page still opens when the gateway is down.
   - `docs/`: every command you write must have been run.
5. Open a PR using the template. State what you ran, on which device/browser/chain, and paste the visible result.

## Style

- English or Chinese in code comments and docs; translations keep the structure and the numbers of the original.
- Never soften the compliance boundary: "non-custodial", "core is closed", "not audited" must stay.
- Small PRs merge fast. A 400-line PR that touches five directories will be asked to split.
- Commit messages: `area: what changed` (e.g. `demo: add TRON deep link`).

## Review and credit

Maintainer review is manual. Expect comments within a few days rather than hours. Merged contributions are credited in the next release notes and in the PR thread. There is no bug bounty and no token, equity or revenue share for contributors — please do not expect one.

## Security

Anything exploitable goes to GitHub Security Advisories, not the issue tracker — see `SECURITY.md`.
