SUMMARY = "Lontium LT9611C firmware"
DESCRIPTION = "${SUMMARY}"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE.Lontium;md5=4ec8dc582ff7295f39e2ca6a7b0be2b6"

SRC_URI = " \
        file://LT9611C.bin \
        file://LICENSE.Lontium \
"

# Place license in standardized location
do_unpack:append () {
    bb.utils.mkdirhier(d.getVar('S'))
    bb.utils.copyfile(d.getVar('UNPACKDIR') + "/LICENSE.Lontium", d.getVar('S') + "/LICENSE.Lontium")
}

do_install:append () {
    install -m 0755 -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${UNPACKDIR}/LT9611C.bin ${D}${nonarch_base_libdir}/firmware
}

FILES:${PN} += " ${nonarch_base_libdir}/firmware/LT9611C.bin"

