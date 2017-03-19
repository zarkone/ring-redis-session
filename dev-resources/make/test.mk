kibit:
	@echo
	@echo "#######################"
	@echo "#  kibit Lint Checks  #"
	@echo "#######################"
	@lein with-profile +test kibit && echo ok

bikeshed:
	@echo
	@echo "##########################"
	@echo "#  bikeshed Lint Checks  #"
	@echo "##########################"
	@lein with-profile +test bikeshed

base-eastwood:
	@echo
	@echo "##########################"
	@echo "#  eastwood Lint Checks  #"
	@echo "##########################"
	@lein with-profile +test eastwood "$(EW_OPTS)"

yagni:
	@echo
	@echo "#######################"
	@echo "#  yagni Lint Checks  #"
	@echo "#######################"
	@lein with-profile +test yagni

eastwood:
	@EW_OPTS="{:namespaces [:source-paths]}" make base-eastwood

lint: kibit eastwood

lint-all: lint bikeshed yagni

lint-unused:
	@EW_OPTS="{:linters [:unused-fn-args :unused-locals :unused-private-vars] :namespaces [:source-paths]}" make base-eastwood

lint-ns:
	@EW_OPTS="{:linters [:unused-namespaces :wrong-ns-form] :namespaces [:source-paths]}" make base-eastwood

check-deps:
	@lein with-profile +test ancient

check-build:
	@echo
	@echo "#################################################"
	@echo "#  Checks builds on supported Clojure versions  #"
	@echo "#################################################"
	@echo
	@echo "Building Clojure 1.5 jar files ..."
	@lein with-profile 1.5 uberjar
	@echo "Building Clojure 1.6 jar files ..."
	@lein with-profile 1.6 uberjar
	@echo "Building Clojure 1.7 jar files ..."
	@lein with-profile 1.7 uberjar
	@echo "Building Clojure 1.8 jar files ..."
	@lein with-profile 1.8 uberjar
	@echo "Building Clojure 1.9 jar files ..."
	@lein with-profile 1.9 uberjar

check:
	@echo
	@echo "################"
	@echo "#  Unit Tests  #"
	@echo "################"
	@echo
	@lein with-profile +test test
