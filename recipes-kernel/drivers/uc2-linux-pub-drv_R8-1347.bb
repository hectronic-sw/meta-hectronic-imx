SUMMARY = "Hectronic UC2 I2C and UART driver"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://uc2-common/init.c;beginline=2;endline=8;md5=25f7ae9d778231a0f66f21830df039d0"

inherit module

EXTRA_OEMAKE += "KDIR=${STAGING_KERNEL_DIR}"

SRC_URI = "http://downloads.hectronic.se/misc/yocto/uc2-linux-pub-drv-${PV}.tar.gz"
SRC_URI[sha256sum] = "607b3f3168cde43842bf9357e662d1dc1773d66ed96b9f9f6e3eac7ff44c0847"

MODULES_MODULE_SYMVERS_LOCATION = "uc2-common"

RPROVIDES:${PN} += "kernel-module-uc2-linux"

KERNEL_MODULE_AUTOLOAD += "uc2-common uc2-i2c"
