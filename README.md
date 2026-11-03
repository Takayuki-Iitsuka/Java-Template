# Java-Template (AtCoder)

AtCoder 向け Java プロジェクトのひな形。ターミナルのみで開発する。

## 構成

```
.
├── Makefile          # build / run / test / clean
├── src/Main.java     # 提出ファイル (class Main, default package)
├── lib/              # 自作ライブラリ置き場 (-sourcepath。提出時は Main.java へ貼り付け)
├── tests/            # サンプル: NAME.in / NAME.out
└── tools/Judge.java  # 簡易ジャッジ (java Judge.java で直接実行)
```

AtCoder は単一ファイル提出のため、`lib/` のコードは提出前に `Main.java` 内へ
static nested class として統合する。

## 前提 (Windows 11 / PowerShell 7)

- JDK (AtCoder の対応バージョンは提出画面の言語一覧で要確認。2025年言語更新では Java 24 とされるが未検証)
- GNU Make: `scoop install make` または `choco install make`

ローカル JDK が 24 未満なら `make RELEASE=21` のように指定する。

## 使い方

```powershell
make build              # コンパイル -> build/
make run                # 実行 (標準入力から)
Get-Content tests/sample-1.in | make run
make test               # tests/*.in を全件判定
make clean
```
