#@TYPE: Machine
#@NAME: Hectronic H6095
#@SOC: i.MX95
#@DESCRIPTION: Machine configuration for H6095 SMARC module
#@MAINTAINER: Robin Stridh <robin.stridh@hectronic.se>

# Based on imx95-19x19-lpddr5-evk.conf

# Machine
MACHINEOVERRIDES =. "mx95:"
MACHINE_BASENAME = "${@d.getVar('MACHINE').split('-')[0]}"

require conf/machine/include/imx-base.inc
require conf/machine/include/arm/armv8-2a/tune-cortexa55.inc

#MACHINE_FEATURES += "pci wifi bluetooth optee"
#MACHINE_FEATURES:append:use-nxp-bsp = " nxpwifi-all-pcie nxpwifi-all-sdio jailhouse dpdk xen"
MACHINE_FEATURES:append:use-nxp-bsp = " optee"

# Devicetree moved to separate recipe
#KERNEL_DEVICETREE = " \
#    hectronic/${KERNEL_DEVICETREE_BASENAME}.dtb \
#"

# Fallback if not set explicitly by machine config
UBOOT_DTB_NAME ?= "h6095.dtb"

UBOOT_CONFIG_BASENAME = "h6095"

IMX_DEFAULT_BOOTLOADER:use-nxp-bsp = "u-boot-imx"

LOADADDR = ""
UBOOT_SUFFIX = "bin"
UBOOT_MAKE_TARGET = ""

SPL_BINARY = "spl/u-boot-spl.bin"

UBOOT_CONFIG ??= "sd"
UBOOT_CONFIG[sd] = "${UBOOT_CONFIG_BASENAME}_defconfig,sdcard"
#UBOOT_CONFIG[sd-ecc] = "${UBOOT_CONFIG_BASENAME}_defconfig,sdcard"
#IMAGE_BOOT_FILES:append = " devicetree/imx95-h6095-*.dtb"

ATF_PLATFORM = "imx95"

OEI_CORE   = "m33"
OEI_SOC    = "mx95"
OEI_BOARD  ?= "mx95lp5"

DDR_TYPE   ?= "lpddr5"

IMXBOOT_VARIANTS = "alt jailhouse netc rpmsg sof"

# Multiple system manager configs by IMXBOOT_VARIANT
SYSTEM_MANAGER_CONFIG = "h6095"
#SYSTEM_MANAGER_CONFIG = \
#    "${@bb.utils.contains('IMXBOOT_VARIANT', 'alt',       'mx95alt', \
#        bb.utils.contains('IMXBOOT_VARIANT', 'jailhouse', 'mx95evkjailhouse', \
#        bb.utils.contains('IMXBOOT_VARIANT', 'netc',      'mx95netc', \
#        bb.utils.contains('IMXBOOT_VARIANT', 'rpmsg',     'mx95evkrpmsg', \
#        bb.utils.contains('IMXBOOT_VARIANT', 'sof',       'mx95evksof', \
#                                                          'mx95evk', d), d), d), d), d)}"

# imx-boot (flash.bin) targets based on UBOOT_CONFIG and IMXBOOT_VARIANT
IMXBOOT_TARGETS_SD = " \
    ${@bb.utils.contains('IMXBOOT_VARIANT', 'alt',       '${IMXBOOT_TARGETS_BASENAME}_alt', \
       bb.utils.contains('IMXBOOT_VARIANT', 'jailhouse', '${IMXBOOT_TARGETS_BASENAME}_jailhouse', \
       bb.utils.contains('IMXBOOT_VARIANT', 'netc',      '${IMXBOOT_TARGETS_BASENAME}_netc', \
       bb.utils.contains('IMXBOOT_VARIANT', 'rpmsg',     '${IMXBOOT_TARGETS_BASENAME}_lpboot_sm_a55', \
       bb.utils.contains('IMXBOOT_VARIANT', 'sof',       '${IMXBOOT_TARGETS_BASENAME}_a55', \
                                                         '${IMXBOOT_TARGETS_BASENAME}_all ${IMXBOOT_TARGETS_BASENAME}_a55', d), d), d), d), d)} \
"

IMXBOOT_TARGETS = " \
    ${@bb.utils.contains('UBOOT_CONFIG', 'sd-ecc', '${IMXBOOT_TARGETS_BASENAME}_all', \
                                                   '${IMXBOOT_TARGETS_SD}', d)} \
"

IMX_BOOT_SOC_TARGET = "iMX95"
IMX_BOOT_SEEK = "32"

# We have to disable SERIAL_CONSOLE due to auto-serial-console
SERIAL_CONSOLES = "115200;ttyLP0"

# Always include kernel modules and sdma fw
MACHINE_ESSENTIAL_EXTRA_RDEPENDS += " kernel-modules firmware-imx-sdma-imx7d"

# WiFi/BT support
MACHINE_ESSENTIAL_EXTRA_RDEPENDS += " kernel-module-nxp-wlan wpa-supplicant iw firmware-nxp-wifi-nxpiw612-sdio"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += " firmware-lontium"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += " kernel-module-uc2-linux"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += " devicetree"

IMAGE_BOOT_FILES:append = " \
    devicetree/${UBOOT_DTB_NAME};${UBOOT_DTB_NAME} \
    devicetree/overlays.txt;overlays.txt \
"

IMX_DEFAULT_BSP = "nxp"
#MACHINE_FEATURES:append:use-nxp-bsp = " nxp9098-pcie"

IMXBOOT_TARGETS_BASENAME = "flash"

# Note: OEI_BOARD to be changed from mx95lp5 to h609x later when needed
#OEI_BOARD  = "h609x"
DDR_TYPE   = "lpddr5"
DDR_FIRMWARE_NAME = " \
    lpddr5_dmem_v202409.bin \
    lpddr5_dmem_qb_v202409.bin \
    lpddr5_imem_v202409.bin \
    lpddr5_imem_qb_v202409.bin \
"
OEI_DDR_CONFIG     = "h609x-imx95lp5_8gb_6400mts_train_timing"
#OEI_DDR_CONFIG_ECC = "MIMX95_LPDDR5_EVK_19X19_6400MTS_FW2024.09_ECC_enabled_timing"

M4_DEFAULT_IMAGE_MX95 = "${@bb.utils.contains('SYSTEM_MANAGER_CONFIG', 'mx95netc', 'imx95-19x19-evk_m7_TCM_netc_share.bin', \
                                                                                   'imx95-19x19-evk_m7_TCM_power_mode_switch.bin', d)}"

#IMAGE_BOOT_FILES:append = " \
#    mcore-demos/imx95-19x19-evk_m7_TCM_flexcan_linux.bin \
#    mcore-demos/imx95-19x19-evk_m7_TCM_netc_share.bin \
#    mcore-demos/imx95-19x19-evk_m7_TCM_power_mode_switch.bin \
#    mcore-demos/imx95-19x19-evk_m7_TCM_rpmsg_lite_pingpong_rtos_linux_remote.bin \
#    mcore-demos/imx95-19x19-evk_m7_TCM_rpmsg_lite_str_echo_rtos.bin \
#    mcore-demos/imx95-19x19-evk_m7_TCM_sai_low_power_audio.bin \
#    ${@bb.utils.contains('COMBINED_FEATURES', 'xen', 'xen', '', d)} \
#"
