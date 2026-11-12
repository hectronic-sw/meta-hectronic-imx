SUMMARY = "Custom device trees for Hectronic boards"
DESCRIPTION = ""
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit devicetree
DEPENDS += "dtc-native"

COMPATIBLE_MACHINE = "^(h6095.*|h6099.*)$"

SRCBRANCH = "linux-6.18.y"
SRCREV = "bc67b99adf74acb0e5aead3a60b26fa8397a4b67"
SRC_URI = "git://github.com/hectronic-sw/hectronic-devicetree;branch=${SRCBRANCH};protocol=https"

DT_FILES_PATH = "${S}/devicetree-${PV}/arm64/freescale"

DT_FILES:h6095 = " \
	imx95-h6095.dts \
	imx95-h6095-smx331.dts \
"

DT_FILES:h6095-smx331 = " \
	imx95-h6095.dts \
	imx95-h6095-smx331.dts \
"

do_deploy:append() {
    mkdir -p ${DEPLOYDIR}/devicetree
    if [ -n ${UBOOT_DTB_OVERLAY} ] ; then
        echo "fdt_overlays=${UBOOT_DTB_OVERLAY}" > ${DEPLOYDIR}/devicetree/overlays.txt
    fi
}


