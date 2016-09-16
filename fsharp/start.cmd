@echo off

if not exist .paket\paket.exe (
    @echo "Downloading Paket"
    .paket\paket.bootstrapper.exe
)

@echo "Restoring dependencies"
.paket\paket.exe restore