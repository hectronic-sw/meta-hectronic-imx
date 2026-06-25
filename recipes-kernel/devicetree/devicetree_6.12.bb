SUMMARY = "Custom device trees for Hectronic boards"
DESCRIPTION = ""
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit devicetree
DEPENDS = "virtual/kernel dtc-native"

COMPATIBLE_MACHINE = "^(h6095.*|h6099.*)$"

SRCBRANCH="linux-6.12.y"
SRCREV = "72d2e3fcf61a71ffbdc9e38cc61cbacf3f8fdff0"
SRC_URI = "https://github.com/hectronic-sw/hectronic-devicetree;branch=${SRCBRANCH};protocol=ssh"

DT_FILES_PATH = "${S}/git/arm64/freescale"
DT_FILES:h6095 = " \
	imx95-h6095.dts \
	imx95-h6095-smx331.dts \
"

do_deploy:append() {
    mkdir -p ${DEPLOYDIR}/devicetree
    if [ -n ${UBOOT_DTB_OVERLAY} ] ; then
        echo "fdt_overlays=${UBOOT_DTB_OVERLAY}" > ${DEPLOYDIR}/devicetree/overlays.txt
    fi
}


