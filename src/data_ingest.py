from __future__ import annotations

import argparse
from pathlib import Path

import pandas as pd
import yfinance as yf


def download_stock_data(ticker: str, start: str, end: str) -> pd.DataFrame:
    """Download historical stock data for a ticker from yFinance."""
    data = yf.download(ticker, start=start, end=end, progress=False)
    if data.empty:
        raise ValueError(f"No data returned for ticker '{ticker}' between {start} and {end}.")
    return data


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Download stock data using yFinance.")
    parser.add_argument("--ticker", default="AAPL", help="Stock ticker symbol (default: AAPL)")
    parser.add_argument("--start", default="2020-01-01", help="Start date in YYYY-MM-DD format")
    parser.add_argument("--end", default="2024-01-01", help="End date in YYYY-MM-DD format")
    parser.add_argument(
        "--output-dir",
        default="data/raw",
        help="Directory where the CSV file will be written (default: data/raw)",
    )
    return parser.parse_args()


def main() -> None:
    args = parse_args()
    output_dir = Path(args.output_dir)
    output_dir.mkdir(parents=True, exist_ok=True)

    df = download_stock_data(args.ticker, args.start, args.end)
    output_file = output_dir / f"{args.ticker.upper()}.csv"
    df.to_csv(output_file)
    print(f"Saved {len(df)} rows to {output_file}")


if __name__ == "__main__":
    main()
