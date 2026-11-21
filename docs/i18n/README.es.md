# Aegis Chain (Español)

> Consola de cobros multicadena no custodial: las claves siguen en manos del comercio, la plataforma solo gestiona facturas, conciliación y el libro de intenciones entre cadenas.

[Índice](../../README.md) · [中文](README.zh.md) · [English](README.en.md) · [Français](README.fr.md) · [한국어](README.ko.md) · [日本語](README.ja.md) · [Русский](README.ru.md) · [Bahasa Indonesia](README.id.md) · [العربية](README.ar.md)

## Qué es

Una pasarela de pago autoalojada más una consola móvil. El comercio crea una factura, el cliente paga on-chain, la pasarela vigila la cadena y liquida la factura. Sin cuentas, sin custodia, sin token.

- **Cobro multicadena** — Ethereum, Base, Arbitrum, Polygon, BNB Chain, además de vigilancia de BTC y TRON mediante indexadores públicos
- **Liquidación on-chain** — el contrato `PaymentHub` libera fondos solo a la dirección del propio comercio, con prueba de liquidación firmada y anti-replay
- **Intenciones entre cadenas** — libro con estados explícitos (source / confirm / credit / done), sin reintentos automáticos, cada salto se concilia
- **Protecciones** — cribado de listas de sanciones antes de emitir la factura, failover multi-RPC con interruptor, finalidad consciente de reorg
- **App móvil** — carcasa Android nativa en tres compilaciones (dev / test / prod); iOS se instala como PWA
- **Internacionalización** — interfaz del producto en cinco idiomas (zh, en, de, fr, es), documentación del repositorio en nueve

## Qué es público

| Abierto | Cerrado |
| --- | --- |
| Documentación, resumen de arquitectura, notas de cumplimiento, política de seguridad, hoja de ruta | Código del motor de pagos, escáneres de cadena, fuente del contrato de liquidación y scripts de despliegue |
| Seguimiento de incidencias, correcciones de traducción, informes de fallos sobre comportamiento documentado | Claves de firma, endpoints de producción, pools de direcciones, configuración del comercio |

Este repositorio es la superficie pública de información y soporte. La implementación central **no es open source** — ver [OPEN-SCOPE.md](../OPEN-SCOPE.md).

## Límite de cumplimiento

Solo software no custodial: sin custodia, sin entrada/salida de fiat, sin token, sin puente propio. Según MiCA (UE) esto es provisión de software, no servicio CASP; añadir custodia, exchange o conversión de fiat exigiría una licencia CASP que un particular no puede obtener. Ver [COMPLIANCE.md](../COMPLIANCE.md).

## Documentos

[Arquitectura](../ARCHITECTURE.md) · [Funciones](../FEATURES.md) · [Cumplimiento](../COMPLIANCE.md) · [Hoja de ruta](../ROADMAP.md) · [Seguridad](../../SECURITY.md) · [Contribuir](../../CONTRIBUTING.md)

## Estado

Versión 0.4.0 — Base mainnet y Base Sepolia verificados contra cadenas reales; 29 pruebas unitarias y 11 comprobaciones end-to-end en verde. Sin auditoría ni endurecimiento de producción; no muevas fondos reales de clientes todavía.
