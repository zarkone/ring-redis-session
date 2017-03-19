DOCS_DIR = $(ROOT_DIR)/docs
CURRENT = $(DOCS_DIR)/current
LOCAL_DOCS_HOST = localhost
LOCAL_DOCS_PORT = 5099

.PHONY: docs

docs-clean:
	@echo
	@echo "################################################"
	@echo "#  Cleaning previous published docs build ...  #"
	@echo "################################################"
	@echo
	@rm -rf $(CURRENT)/*
	@rm -rf resources/public/docs/admin/current

docs: docs-clean
	@echo
	@echo "######################"
	@echo "#  Building docs ... #"
	@echo "######################"
	@echo
	@lein with-profile +docs codox

docs-serve: docs
	@echo
	@echo "Running docs server on http://$(LOCAL_DOCS_HOST):$(LOCAL_DOCS_PORT) ... "
	@echo
	@lein with-profile +docs simpleton $(LOCAL_DOCS_PORT) file :from $(CURRENT)
