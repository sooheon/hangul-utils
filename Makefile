.PHONY: dist/hangul-utils.jar pom.xml deploy

pom.xml: deps.edn
	clojure -Spom
	sed -i '' '/^ *$/d' "$@"

dist/hangul-utils.jar: deps.edn src/**/*
	mkdir -p dist
	clojure -A:pack mach.pack.alpha.skinny --project-path "$@"

deploy: pom.xml dist/hangul-utils.jar
	clojure -A:deploy
