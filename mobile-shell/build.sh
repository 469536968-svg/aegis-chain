#!/usr/bin/env bash
# Android APK 构建（不依赖 Gradle）：aapt2 + javac + d8 + zipalign + apksigner
#
# 用法：./build.sh dev | test | prod
#
# 三个变体共用同一份 Java/资源/网页代码，只有 BuildConfig 常量、包名后缀、应用名不同，
# 保证开发版 / 测试版 / 公开版功能完全一致。
set -euo pipefail

ENV="${1:-dev}"
HERE="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SDK="${ANDROID_SDK_ROOT:-/tmp/sdkroot}"
BT="$SDK/build-tools/android-14"
AJAR="$SDK/platforms/android-34/android.jar"
OUT="$HERE/build/$ENV"

[ -x "$BT/aapt2" ] || { echo "缺少 build-tools: $BT"; exit 1; }
[ -f "$AJAR" ] || { echo "缺少 android.jar: $AJAR"; exit 1; }

case "$ENV" in
  dev)  SUFFIX=".dev";  APPNAME="Aegis Dev";   BASE="http://127.0.0.1:8787" ;;
  test) SUFFIX=".test"; APPNAME="Aegis Test";  BASE="https://test.aegis-chain.example" ;;
  prod) SUFFIX="";      APPNAME="Aegis Chain"; BASE="https://app.aegis-chain.example" ;;
  *) echo "用法: $0 dev|test|prod"; exit 1 ;;
esac

rm -rf "$OUT"; mkdir -p "$OUT"/{res,classes,gen,dex}

# ---- 1. 生成 BuildConfig（唯一按环境变化的源码）----
mkdir -p "$OUT/gen/dev/aegischain/app"
cat > "$OUT/gen/dev/aegischain/app/BuildConfig.java" <<EOF
package dev.aegischain.app;

public final class BuildConfig {
    public static final String APP_ENV = "$ENV";
    public static final String APP_LABEL = "$APPNAME";
    public static final String DEFAULT_BASE_URL = "$BASE";
    public static final String FEATURE_SET = "v0.5.0-full";
}
EOF

# ---- 2. 复制资源树并注入应用名（占位模板不进最终包）----
cp -r "$HERE/res/." "$OUT/res/"
rm -f "$OUT/res/values/strings.template.xml"
sed "s/@APP_NAME@/$APPNAME/" "$HERE/res/values/strings.template.xml" > "$OUT/res/values/strings.xml"

# ---- 3. 资源编译 + 链接（含 assets 与图标）----
"$BT/aapt2" compile --dir "$OUT/res" -o "$OUT/res.zip"
"$BT/aapt2" link -o "$OUT/base.apk" \
  -I "$AJAR" \
  --manifest "$HERE/AndroidManifest.xml" \
  -A "$HERE/assets" \
  --java "$OUT/gen" \
  --min-sdk-version 24 --target-sdk-version 34 \
  --version-code 5 --version-name 0.5.0 \
  --rename-manifest-package "dev.aegischain.app$SUFFIX" \
  "$OUT/res.zip"

# ---- 4. 编译 Java（只有 MainActivity + BuildConfig）----
find "$OUT/gen" "$HERE/src" -name '*.java' > "$OUT/sources.txt"
if ! javac -source 8 -target 8 -bootclasspath "$AJAR" -classpath "$AJAR" \
  -d "$OUT/classes" @"$OUT/sources.txt"; then
  echo "javac failed"; exit 1
fi

# ---- 5. dex + 打包 ---- 
"$BT/d8" --min-api 24 --lib "$AJAR" --output "$OUT/dex" $(find "$OUT/classes" -name '*.class')
[ -f "$OUT/dex/classes.dex" ] || { echo "缺少 classes.dex"; exit 1; }
cd "$OUT"
cp base.apk unsigned.apk
# classes.dex 必须在 APK 根目录，放到 dex/ 子目录会装不上
cp dex/classes.dex ./classes.dex
zip -q -u unsigned.apk classes.dex
unzip -l unsigned.apk | grep -qE " classes.dex$" || { echo "dex 未打进 APK 根目录"; exit 1; }
"$BT/zipalign" -f 4 unsigned.apk aligned.apk

# ---- 6. 调试签名（正式上架换成你自己的 release keystore）----
KS="$HERE/debug.keystore"
if [ ! -f "$KS" ]; then
  keytool -genkeypair -v -keystore "$KS" -storepass android -keypass android \
    -alias aegis -keyalg RSA -keysize 2048 -validity 10000 \
    -dname "CN=Aegis Dev, O=Aegis, C=CN" >/dev/null 2>&1
fi
"$BT/apksigner" sign --ks "$KS" --ks-pass pass:android --key-pass pass:android \
  --ks-key-alias aegis --out "aegis-$ENV.apk" aligned.apk
"$BT/apksigner" verify "aegis-$ENV.apk" >/dev/null && echo "signature ok"

echo "APK: $OUT/aegis-$ENV.apk ($(du -h "$OUT/aegis-$ENV.apk" | cut -f1))"
echo "package: dev.aegischain.app$SUFFIX · app: $APPNAME · gateway: $BASE"
