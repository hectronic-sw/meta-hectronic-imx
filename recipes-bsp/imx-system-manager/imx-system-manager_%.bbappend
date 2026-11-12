FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:h6095 = " file://0001-Add-h6095-board.patch"

SYSTEM_MANAGER_CONFIG = "h6095"

# Disable console
SYSTEM_MANAGER_MONITOR_MODE = "0"

