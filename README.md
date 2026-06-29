meta-hectronic-imx
==================

This Yocto meta layer adds BSP support for the Hectronic i.MX platforms to be 
used together with the official NXP repos:

https://github.com/nxp-imx/imx-manifest

The different branches in this repository corresponds to different releases of the NXP BSP:
  walnascar works with manifest imx-6.12.49-2.2.0.xml from NXP's imx-linux-walnascar branch

Build instructions for each branch is found in this README file in respective branch


Build instructions using the official NXP Yocto BSP framework
-------------------------------------------------------------
Download NXP BSP:
```
mkdir -p nxp-yocto && cd $_
repo init -u https://github.com/hectronic-sw/hectronic-manifest -b walnascar -m repo/nxp-imx.xml
repo sync
```

Setup build directory:
```
MACHINE=h6095-smx331 DISTRO=fsl-imx-xwayland . imx-setup-release.sh -b build-h6095
echo 'BBLAYERS += "${BSPDIR}/sources/meta-hectronic-imx"' >> conf/bblayers.conf
```

Add local changes (to speedup rebuild during development)
 - conf/local.conf
    - INHERIT += "rm_work"
 - conf/site.conf
    - DL_DIR ?= "${BSPDIR}/../downloads/"
    - SSTATE_DIR ?= "${BSPDIR}/../sstate-cache/"

Build image:
```
bitbake core-image-minimal
```

Or any other more potent image:
  - core-image-base
  - imx-image-core
  - imx-image-multimedia
