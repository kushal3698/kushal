# AI-Powered Stock Market Prediction & Trading Bot

A modular Python starter project for building an AI/ML-powered stock prediction and trading assistant.

## Features (Planned)

- Stock data ingestion from yFinance
- Price prediction models (baseline ML, LSTM, Transformers)
- News sentiment analysis
- Risk analysis and portfolio optimization
- AI-based trading recommendations
- Explainability with SHAP
- Streamlit dashboard for visualization

## Project Structure

```text
.
├── data/
│   ├── raw/
│   └── processed/
├── models/
├── notebooks/
├── src/
│   ├── __init__.py
│   └── data_ingest.py
├── Makefile
├── requirements.txt
└── README.md
```

## Quick Start

```bash
python3 -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

Download sample stock data:

```bash
make ingest TICKER=AAPL START=2020-01-01 END=2024-01-01
```

The downloaded CSV is saved to `data/raw/`.

## Usage

You can also run ingestion directly:

```bash
python src/data_ingest.py --ticker AAPL --start 2020-01-01 --end 2024-01-01
```

## Roadmap

- [x] Project scaffold and baseline data ingestion
- [ ] Add preprocessing and feature engineering pipeline
- [ ] Build baseline prediction model
- [ ] Add LSTM/Transformer forecasting experiments
- [ ] Integrate sentiment analysis from financial news
- [ ] Add backtesting and risk metrics
- [ ] Build Streamlit dashboard
- [ ] Add explainable AI outputs

## Disclaimer

This project is for educational and research purposes only, and is **not** financial advice.
