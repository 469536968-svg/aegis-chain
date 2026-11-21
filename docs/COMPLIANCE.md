# Compliance notes (EU / individual developer)

> This is an engineering self-assessment, not legal advice. Before serving real customers, have a lawyer in your
> jurisdiction review it.

## What the platform does and does not do

| Does | Does not |
| --- | --- |
| Generates invoices, watches chains, reconciles payments, emits webhooks | Hold customer funds or private keys |
| Deploys a settlement contract that pays only the merchant | Exchange crypto ↔ fiat, or crypto ↔ crypto for customers |
| Screens deposit addresses against sanctions lists | Issue a token, run a bridge, or operate a trading venue |

## MiCA (EU) 2023/1114

- **Custody / exchange / transfer on behalf of clients** are CASP activities requiring authorisation. An individual
  cannot obtain CASP authorisation; MiCA expects a legal person with governance, capital and audits.
- **Non-custodial software** that users run themselves, where the provider never controls the keys or the funds, is
  outside CASP authorisation. The platform is built to stay on that side of the line, and the boundary is enforced
  in code, not just in text: no key material, no balance, no conversion path.
- **Marketing** must not present the product as a custodian, exchange or investment service. This repository avoids
  such claims deliberately.
- **If the model changes** — adding custody, fiat ramps, a token, or a bridge — the CASP question reopens and the
  project must stop and restructure before shipping.

## Other EU obligations to keep in mind

- **GDPR** — merchant and customer data minimisation: no accounts, no KYC data stored by the platform; logs should
  be IP-shortened and retention-bounded. The operator of a self-hosted instance is the controller.
- **DSA / consumer law** — if the app is distributed through a store, contact and legal information must be accurate.
- **Sanctions** — EU restrictive measures apply to addresses as well as persons; screening before an invoice exists
  is the practical control.
- **Tax** — accepting crypto for goods or services is a taxable event in most member states; the merchant, not the
  platform, is the taxable person.

## Risk statement

Not audited. Not hardened. Suitable for development, demos and controlled pilots with small amounts after review —
not for custodial use or high-value merchant traffic.
