FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:h6095 = " \
	file://0001-Add-memid-support.patch \
	file://0002-Add-H609x-training-data.patch \
"

EXTRA_OEMAKE:remove = "DEBUG=1"

