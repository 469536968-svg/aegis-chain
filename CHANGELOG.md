# Changelog

## [Unreleased]

### Added — support / donation page
- `GET /support`（别名 `/donate`）：多链收款地址 + 本地生成二维码 + 钱包深链（EIP-681 / BIP-21 / tron:），5 语种
- `GET /api/support`：捐赠方式与配置状态；未配置时回落演示地址并显著告警
- 支持者名单：默认关闭（`DONATE_LIST=on` 才开），条目入库前清洗 XSS / 非法日期 / 负金额 / 超长
- 地址格式服务端校验，一条不合法即整体报配置错误，不展示可能打错的地址
- 功能清单新增 `support.donate`（共 24 项），三环境一致性校验同步

### Added — support page / donation
`/support` renders receiving addresses with locally generated QR codes and wallet deep links in five languages; no third-party QR service ever sees the address. Open amount, no invoice, no settlement path. Supporter list is opt-in only. 7 new unit tests.

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
