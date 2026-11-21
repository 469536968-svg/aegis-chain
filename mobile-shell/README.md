# mobile-shell/ — 安卓壳（MIT）

原生 Android WebView 壳：把网关页面装进 App。**功能全部来自网关同一套代码**，壳只负责承载、进度条、返回键、离线页和「长按标题改网关地址」。

三个构建变体（dev / test / prod）共用同一份 Java 与资源，只有 `BuildConfig` 的 `APP_ENV` / `APP_LABEL` / `DEFAULT_BASE_URL`、包名后缀和应用名不同——所以三版功能严格一致，且可同时安装对照。

## 构建（不依赖 Gradle）

需要 JDK 17 + Android SDK（`platforms/android-34` + `build-tools 34`）：

```bash
export ANDROID_SDK_ROOT=/path/to/android-sdk
./build.sh dev      # → build/dev/aegis-dev.apk
./build.sh test
./build.sh prod
```

脚本内部走 aapt2 + javac + d8 + zipalign + apksigner，直出 APK。

## 签名

默认用脚本自建的 `debug.keystore`（口令 `android`），**只适合自测与内部分发**。上架必须换成你自己的 release keystore 并离线备份；`*.keystore` 已在 `.gitignore` 里，别提交。

## 壳内行为

- 顶部细进度条 = 页面加载进度
- 返回键 → 网页后退，退到底才退出
- 加载失败 → 内置离线页 `assets/www/offline.html`
- 长按标题 → 改网关地址（存 SharedPreferences）
- 只放行明文 http 用于本地/内网自建，公网必须 https

## 不在壳里做什么

壳不生成私钥、不签名交易、不保存助记词；它只是商户控制台的容器。
