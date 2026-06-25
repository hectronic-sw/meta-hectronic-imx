FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILESEXTRAPATHS:prepend := "${THISDIR}/files/${MACHINE_BASENAME}:"

SRC_URI = "git://github.com/nxp-imx/linux-imx.git;protocol=https;branch=lf-6.12.y"
SRCREV = "df24f9428e38740256a410b983003a478e72a7c0"

# Note: the linux-imx disables the standard yocto way to add kernel config
# fragments. They must explicitly be added via DELTA_KERNEL_DEFCONFIG
DELTA_KERNEL_DEFCONFIG:append = " \
        enable-ramdisk.cfg \
        enable-devices.cfg \
        disable-devices.cfg \
"

#FIXME DELTA_KERNEL_DEFCONFIG:append:h6095 won't work?!?!

SRC_URI += " \
	file://0001-imx8mq-set-bus-frequency-scaling-default-to-disabled.patch \
	file://0002-Add-mxl-86110-driver.patch \
	file://0003-drm-bridge-ti-sn65dsi83-Increase-delay-after-reset-t.patch \
	file://0004-drm-bridge-Add-support-for-Lontium-LT9611C-bridge.patch \
	file://0005-backlight-Add-driver-for-TI-LP8863-LED-backlight-dri.patch \
	file://enable-ramdisk.cfg \
	file://enable-devices.cfg \
	file://disable-devices.cfg \
"

