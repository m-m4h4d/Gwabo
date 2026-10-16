# Gwabo landing page

Static marketing page for the Gwabo Android app. No build step.

## Deploy on Vercel (free Hobby plan)

1. Import the `Gwabo` repo at https://vercel.com/new.
2. Set **Root Directory** to `landing`.
3. Framework preset: **Other**. Leave build and output settings empty.
4. Deploy.

## Before going live

- `gwabo.apk` is a debug build of the app, served from this folder. To update it,
  run `./gradlew assembleDebug` from the repo root and copy
  `app/build/outputs/apk/debug/app-debug.apk` over `landing/gwabo.apk`.
  Replace it with a signed release build before a public launch.
- When the app is on Google Play, swap the "coming soon" button for a real Play Store link.
