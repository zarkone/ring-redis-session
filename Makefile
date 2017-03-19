PROJ = ring-redis-session
ROOT_DIR = $(shell pwd)

REPO = $(shell git config --get remote.origin.url)

OS := $(shell uname -s)
ifeq ($(OS),Linux)
	HOST = $(HOSTNAME)
endif
ifeq ($(OS),Darwin)
	HOST = $(shell scutil --get ComputerName)
endif

include dev-resources/make/docs.mk
include dev-resources/make/test.mk
include dev-resources/make/git.mk
