# Aegis Chain (Français)

> Console d'encaissement multi-chaînes non dépositaire : les clés restent chez le commerçant, la plateforme ne gère que les factures, le rapprochement et le registre d'intentions inter-chaînes.

[Index](../../README.md) · [中文](README.zh.md) · [English](README.en.md) · [한국어](README.ko.md) · [日本語](README.ja.md) · [Русский](README.ru.md) · [Bahasa Indonesia](README.id.md) · [Español](README.es.md) · [العربية](README.ar.md)

## Présentation

Une passerelle de paiement auto-hébergée et une console mobile. Le commerçant crée une facture, le client paie on-chain, la passerelle surveille la chaîne et règle la facture. Aucun compte, aucune garde des fonds, aucun jeton.

- **Encaissement multi-chaînes** — Ethereum, Base, Arbitrum, Polygon, BNB Chain, plus la surveillance BTC et TRON via des indexeurs publics
- **Règlement on-chain** — le contrat `PaymentHub` ne libère les fonds que vers l'adresse du commerçant, avec une preuve de règlement signée et anti-rejeu
- **Intentions inter-chaînes** — registre à états explicites (source / confirm / credit / done), sans relance automatique, chaque étape étant rapprochée
- **Garde-fous** — filtrage des listes de sanctions avant émission, bascule multi-RPC avec disjoncteur, finalité tenant compte des réorganisations
- **Application mobile** — enveloppe Android native en trois builds (dev / test / prod) ; iOS s'installe en PWA
- **Internationalisation** — interface produit en cinq langues (zh, en, de, fr, es), documentation du dépôt en neuf

## Ce qui est public

| Ouvert | Fermé |
| --- | --- |
| Documentation, synthèse d'architecture, notes de conformité, politique de sécurité, feuille de route | Code du moteur de paiement, scanners de chaînes, source du contrat de règlement et scripts de déploiement |
| Suivi des tickets, corrections de traduction, rapports de bogues sur le comportement documenté | Clés de signature, points de terminaison de production, pools d'adresses, configuration marchande |

Le dépôt est la surface publique d'information et de support. L'implémentation centrale **n'est pas open source** — voir [OPEN-SCOPE.md](../OPEN-SCOPE.md).

## Limite de conformité

Logiciel non dépositaire uniquement : pas de garde, pas de conversion fiat, pas de jeton, pas de bridge maison. Selon MiCA (UE), c'est de la fourniture de logiciel et non un service de CASP ; ajouter la garde, l'échange ou la conversion fiat exigerait une licence CASP qu'un particulier ne peut obtenir. Voir [COMPLIANCE.md](../COMPLIANCE.md).

## Documents

[Architecture](../ARCHITECTURE.md) · [Fonctionnalités](../FEATURES.md) · [Conformité](../COMPLIANCE.md) · [Feuille de route](../ROADMAP.md) · [Sécurité](../../SECURITY.md) · [Contribuer](../../CONTRIBUTING.md)

## État

Version 0.4.0 — Base mainnet et Base Sepolia vérifiés sur chaînes réelles ; 29 tests unitaires et 11 contrôles de bout en bout au vert. Non audité, non durci pour la production ; ne pas y faire transiter de vrais fonds clients.
