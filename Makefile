# AtCoder 向け Java ひな形 Makefile (GNU Make)
# Windows: PowerShell 7 (pwsh) を使用。 `scoop install make` 等で GNU Make を導入すること。

ifeq ($(OS),Windows_NT)
SHELL        := pwsh.exe
.SHELLFLAGS  := -NoProfile -Command
RM_BUILD     = if (Test-Path $(BUILD)) { Remove-Item -Recurse -Force $(BUILD) }
else
RM_BUILD     = rm -rf $(BUILD)
endif

SRC      := src
LIB      := lib
BUILD    := build
TESTS    := tests
MAIN     := Main
JAVAC    ?= javac
JAVA     ?= java
# AtCoder 現行は Java 24。ローカル JDK が古い場合は make RELEASE=21 のように指定
RELEASE  ?=
JAVAC_FLAGS := -encoding UTF-8 -Xlint:all $(if $(RELEASE),--release $(RELEASE))
JAVA_FLAGS  := -Xss512m -XX:+UseSerialGC

.PHONY: all build run test check clean help
.DEFAULT_GOAL := help

all: build

build: ## コンパイル (src/Main.java -> build/)
	$(JAVAC) $(JAVAC_FLAGS) -d $(BUILD) -sourcepath $(LIB) $(SRC)/$(MAIN).java

run: build ## 標準入力を渡して実行 (例: make run < tests/sample-1.in)
	$(JAVA) $(JAVA_FLAGS) -cp $(BUILD) $(MAIN)

test: build ## tests/*.in と *.out を突き合わせて判定
	$(JAVA) $(JAVA_FLAGS) tools/Judge.java $(BUILD) $(TESTS) "$(JAVA)" "$(JAVA_FLAGS)"

check: test ## test の別名

clean: ## build/ を削除
	$(RM_BUILD)

help: ## コマンド一覧
	@echo "targets: build run test clean  (RELEASE=21 で --release 指定)"
