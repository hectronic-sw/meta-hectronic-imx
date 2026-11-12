FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILESEXTRAPATHS:prepend := "${THISDIR}/files/${MACHINE_BASENAME}:"

SRC_URI += " \
	file://0001-imx8mq-set-bus-frequency-scaling-default-to-disabled.patch \
	file://0003-drm-bridge-ti-sn65dsi83-Increase-delay-after-reset-t.patch \
	file://0004-drm-bridge-Add-support-for-Lontium-LT9611C-bridge.patch \
	file://0005-backlight-Add-driver-for-TI-LP8863-LED-backlight-dri.patch \
	file://0006-disable-eth-led-blink.patch \
	file://0007-add-usbhub-2422-support.patch \
	file://0008-add-fxl6408-irq-support.patch \
	file://enable-ramdisk.cfg \
	file://enable-devices.cfg \
"

