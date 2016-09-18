#!/bin/bash

if [ ! -f ".paket/paket.exe" ]; then
  echo "Downloading Paket"
  mono .paket/paket.bootstrapper.exe
fi

echo "Restoring dependencies"
mono .paket/paket.exe restore