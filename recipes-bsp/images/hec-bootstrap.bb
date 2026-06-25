
DESCRIPTION = "Small image to be booted from usb via uuu for rescue/install"

LICENSE = "MIT"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILESEXTRAPATHS:prepend := "${THISDIR}/files/${MACHINE_BASENAME}:"

SRC_URI = " \
	file://uuu.auto \
"

PACKAGE_INSTALL = " \
	packagegroup-core-boot \
	packagegroup-base \
	initramfs-framework-base \
	initramfs-module-udev \
	${VIRTUAL-RUNTIME_base-utils} \
	udev \
	dropbear \
	base-files \
	base-passwd \
	shadow \
	imx-boot \
"

PACKAGE_EXCLUDE = "kernel-image-*"

IMAGE_FSTYPES = " squashfs.u-boot"

EXTRA_IMAGEDEPENDS += "virtual/kernel virtual/bootloader devicetree imx-boot"

# Link /etc/dropbear to ramdisk, which allows creating keys on-the-fly
do_rootfs:append() {
    import os

    os.rmdir("%s/etc/dropbear" % d.getVar('IMAGE_ROOTFS') )
    os.system("ln -s /var/volatile %s/etc/dropbear" % d.getVar('IMAGE_ROOTFS') )
}

# Create a tar ball with all necessary files to bootstrap via USB
do_deploy_tgz() {
	TGTDIR=${DEPLOY_DIR_IMAGE}/${MACHINE}-bootstrap
	BASENAME=`echo ${MACHINE} | sed -re 's!-.*!!'`

	if [ -e ${TGTDIR} ] ; then
		rm -rf ${TGTDIR}
	fi

	mkdir -p ${TGTDIR}
	cd ${TGTDIR}
	cp -L ${DEPLOY_DIR_IMAGE}/hec-bootstrap-${MACHINE}*.rootfs.squashfs.u-boot ${TGTDIR}
	ln -s `ls -1 *.rootfs.squashfs.u-boot` rootfs.squashfs.u-boot
	cp ${DEPLOY_DIR_IMAGE}/imx-boot-${MACHINE}-sd.bin-flash_a55 ${TGTDIR}
	ln -s `ls -1 imx-boot*flash*` imx-boot-sd.bin

	cp ${DEPLOY_DIR_IMAGE}/Image-${MACHINE}.bin ${TGTDIR}
	ln -s Image-${MACHINE}.bin Image

	if [ "${UBOOT_DTB_NAME}" != "" ] ; then
		cp ${DEPLOY_DIR_IMAGE}/devicetree/${UBOOT_DTB_NAME} ${TGTDIR}
	fi

	if [ "${UBOOT_DTB_OVERLAY}" != "" ] ; then
		cp ${DEPLOY_DIR_IMAGE}/devicetree/${UBOOT_DTB_OVERLAY} ${TGTDIR}
	fi

	cp ${THISDIR}/files/${BASENAME}/uuu.auto ${TGTDIR}
	sed -re "s!UBOOT_DTB_NAME!${UBOOT_DTB_NAME}!" -i ${TGTDIR}/uuu.auto

	cd ${DEPLOY_DIR_IMAGE} && tar zcf ${DEPLOY_DIR_IMAGE}/${MACHINE}-bootstrap.tgz `basename ${TGTDIR}`
}

addtask deploy_tgz after do_image_complete before do_build

inherit core-image

