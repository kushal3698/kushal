TESTOPTS := ""
TESTPATH := "tests/"
UV_OPTS := --default-index https://pypi.org/simple

.PHONY: install
install: ## Install virtual environment with uv
	@echo "🚀 Creating virtual environment using uv"
	@UV_NO_CONFIG=1 uv sync $(UV_OPTS) --all-groups --frozen

.PHONY: check
check: ## Check lock file consistency and run static code analysis
	@echo "🚀 Checking lock file consistency with 'pyproject.toml'"
	@UV_NO_CONFIG=1 uv lock $(UV_OPTS) --locked
	@echo "🚀 Linting code: Running ruff"
	@UV_NO_CONFIG=1 uv run $(UV_OPTS) ruff check --fix
	@echo "🚀 Static type checking: Running basedpyright"
	@UV_NO_CONFIG=1 uv run $(UV_OPTS) basedpyright youtube_analyst
	@echo "🚀 Checking for obsolete dependencies: Running deptry"
	@UV_NO_CONFIG=1 uv run $(UV_OPTS) deptry youtube_analyst

.PHONY: test
test: ## Run all tests
	@echo "🚀 Testing code: Running pytest"
	@UV_NO_CONFIG=1 uv run $(UV_OPTS) pytest $(TESTPATH) $(TESTOPTS) \
		--cov \
		--cov-config=pyproject.toml \
		--cov-report=xml \
		--cov-report=term \
		--cov-report=term-missing

.PHONY: web
web: ## Run the ADK web demo server
	@UV_NO_CONFIG=1 uv run $(UV_OPTS) adk web --reload .

.PHONY: api_server
api_server: ## Run the ADK FastAPI server
	@UV_NO_CONFIG=1 uv run $(UV_OPTS) adk api_server .

.PHONY: cli
cli: ## Run the ADK CLI interface
	@UV_NO_CONFIG=1 uv run $(UV_OPTS) adk run youtube_analyst

.PHONY: help
help:
	@uv run python -c "import re; \
	[[print(f'\033[36m{m[0]:<20}\033[0m {m[1]}') for m in re.findall(r'^([a-zA-Z_-]+):.*?## (.*)$$', open(makefile).read(), re.M)] for makefile in ('$(MAKEFILE_LIST)').strip().split()]"

.DEFAULT_GOAL := help

# --- Commands from Agent Starter Pack ---

backend: deploy

deploy:
	# Export dependencies to requirements file using uv export.
	(uv export --no-hashes --no-header --no-dev --no-emit-project --no-annotate > youtube_analyst/app_utils/.requirements.txt 2>/dev/null || \
	uv export --no-hashes --no-header --no-dev --no-emit-project > youtube_analyst/app_utils/.requirements.txt) && \
	uv run -m youtube_analyst.app_utils.deploy \
		--source-packages=./youtube_analyst \
		--entrypoint-module=youtube_analyst.agent_engine_app \
		--entrypoint-object=agent_engine \
		--requirements-file=youtube_analyst/app_utils/.requirements.txt \
		$(if $(AGENT_IDENTITY),--agent-identity) \
		$(if $(filter command line,$(origin SECRETS)),--set-secrets="$(SECRETS)")

eval:
	@echo "==============================================================================="
	@echo "| Running Agent Evaluation                                                    |"
	@echo "==============================================================================="
	uv sync --dev --extra eval
	uv run adk eval ./youtube_analyst $${EVALSET:-tests/eval/evalsets/basic.evalset.json} \
		$(if $(EVAL_CONFIG),--config_file_path=$(EVAL_CONFIG),$(if $(wildcard tests/eval/eval_config.json),--config_file_path=tests/eval/eval_config.json,))

eval-all:
	@echo "==============================================================================="
	@echo "| Running All Evalsets                                                        |"
	@echo "==============================================================================="
	@for evalset in tests/eval/evalsets/*.evalset.json; do \
		echo ""; \
		echo "▶ Running: $$evalset"; \
		$(MAKE) eval EVALSET=$$evalset || exit 1; \
	done
	@echo ""
	@echo "✅ All evalsets completed"

lint:
	uv sync --dev --extra lint
	uv run codespell
	uv run ruff check . --diff
	uv run ruff format . --check --diff
	uv run ty check .

playground:
	@echo "==============================================================================="
	@echo "| 🚀 Starting your agent playground...                                        |"
	@echo "|                                                                             |"
	@echo "| 💡 Try asking: Find the top 5 videos about 'Generative AI' from the last month and plot their view counts.|"
	@echo "|                                                                             |"
	@echo "| 🔍 IMPORTANT: Select the 'youtube_analyst' folder to interact with your agent.          |"
	@echo "==============================================================================="
	uv run adk web . --port 8501 --reload_agents

register-gemini-enterprise:
	@uvx agent-starter-pack@0.41.0 register-gemini-enterprise

