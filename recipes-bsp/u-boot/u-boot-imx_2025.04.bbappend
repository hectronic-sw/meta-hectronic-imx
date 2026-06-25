
SRC_URI = "git://github.com/nxp-imx/uboot-imx.git;protocol=https;branch=lf_v2025.04"
SRCREV = "4ddbad60eff308a5b356fb9ab8734ac382ddd692"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI:append = " \
	file://0001-Add-mxl-8611x.c-driver.patch \
	file://0002-Add-h6095-support.patch \
"

INSANE_SKIP:${PN} += "patch-status"

# Dynamically update the default dtb to load. 
do_patch:append() {
    import os
    os.system("sed -re 's!^(CONFIG_PREBOOT=.*setenv fdtfile ).*(\")!\\1%s\\2!' -i %s/configs/%s_defconfig" % (d.getVar('UBOOT_DTB_NAME'), d.getVar('S'), d.getVar('UBOOT_CONFIG_BASENAME')))
}

