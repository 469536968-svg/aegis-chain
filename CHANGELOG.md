# Changelog

## [0.4.0] — 2026-11-21

First public release / 首个公开发行版:
https://github.com/ugjdygh/aegis-chain/releases/tag/v0.4.0

### Added
- Three Android builds (`aegis-dev.apk` / `aegis-test.apk` / `aegis-prod.apk`), distinct package names, installable side by side
- Three-environment feature-parity gate, `scripts/check-parity.mjs` (28 checks)
- Single feature manifest `src/features.js` exposed as `GET /api/features`
- Nine-language public documentation (zh / en / fr / ru / es / ar / ko / ja / id)
- In-app offline page and long-press-to-change-gateway capability

### Verified
- Base mainnet (8453) and Base Sepolia (84532) checked against live chains
- 29 unit tests and 11 end-to-end checks passing

### Known limitations
- Debug-signed, not audited, not production-hardened
- Test and production builds ship placeholder gateway domains
- HarmonyOS NEXT unsupported (cannot install APKs)

### English summary
Three Android builds with an identical feature set, a 28-check parity gate, a shared feature manifest at
`/api/features`, and documentation in nine languages. Base mainnet and Base Sepolia verified against live chains.
Debug-signed and unaudited — do not route real customer funds through it.
