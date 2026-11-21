# Aegis Chain (Bahasa Indonesia)

> Konsol pembayaran multi-chain non-kustodial: kunci pribadi tetap di tangan merchant, platform hanya menangani invoice, rekonsiliasi, dan buku besar intent lintas rantai.

[Indeks](../../README.md) · [中文](README.zh.md) · [English](README.en.md) · [Français](README.fr.md) · [한국어](README.ko.md) · [日本語](README.ja.md) · [Русский](README.ru.md) · [Español](README.es.md) · [العربية](README.ar.md)

## Ringkasan

Gerbang pembayaran yang dihosting sendiri plus konsol seluler. Merchant membuat invoice, pelanggan membayar on-chain, gerbang memantau rantai dan menyelesaikan invoice. Tanpa akun, tanpa kustodian, tanpa token.

- **Penerimaan multi-chain** — Ethereum, Base, Arbitrum, Polygon, BNB Chain, serta pemantauan BTC dan TRON lewat indekser publik
- **Penyelesaian on-chain** — kontrak `PaymentHub` hanya melepas dana ke alamat merchant sendiri, dengan bukti penyelesaian bertanda tangan dan anti-replay
- **Intent lintas rantai** — buku besar dengan status eksplisit (source / confirm / credit / done), tanpa percobaan ulang otomatis, setiap tahap direkonsiliasi
- **Pengaman** — penyaringan daftar sanksi sebelum invoice terbit, failover multi-RPC dengan pemutus arus, finalitas yang memperhitungkan reorg
- **Aplikasi seluler** — shell Android native dalam tiga build (dev / test / prod); iOS dipasang sebagai PWA
- **Internasionalisasi** — antarmuka produk dalam lima bahasa (zh, en, de, fr, es), dokumentasi repositori dalam sembilan bahasa

## Cakupan publik

| Terbuka | Tertutup |
| --- | --- |
| Dokumentasi, ringkasan arsitektur, catatan kepatuhan, kebijakan keamanan, peta jalan | Kode mesin pembayaran, pemindai rantai, sumber kontrak penyelesaian dan skrip deploy |
| Pelacak isu, koreksi terjemahan, laporan bug atas perilaku terdokumentasi | Kunci penandatanganan, endpoint produksi, kumpulan alamat, konfigurasi merchant |

Repositori ini adalah kanal informasi dan dukungan publik. Implementasi inti **tidak open source** — lihat [OPEN-SCOPE.md](../OPEN-SCOPE.md).

## Batas kepatuhan

Hanya perangkat lunak non-kustodial: tanpa kustodi, tanpa on/off-ramp fiat, tanpa token, tanpa bridge buatan sendiri. Menurut MiCA (UE), ini penyediaan perangkat lunak, bukan layanan CASP; menambahkan kustodi, pertukaran, atau konversi fiat akan memerlukan lisensi CASP yang tidak dapat diperoleh perorangan. Lihat [COMPLIANCE.md](../COMPLIANCE.md).

## Dokumen

[Arsitektur](../ARCHITECTURE.md) · [Fitur](../FEATURES.md) · [Kepatuhan](../COMPLIANCE.md) · [Peta jalan](../ROADMAP.md) · [Keamanan](../../SECURITY.md) · [Kontribusi](../../CONTRIBUTING.md)

## Status

Versi 0.4.0 — Base mainnet dan Base Sepolia diverifikasi pada rantai nyata; 29 tes unit dan 11 pemeriksaan end-to-end lulus. Belum diaudit dan belum diperkeras untuk produksi; jangan lewatkan dana pelanggan nyata.
