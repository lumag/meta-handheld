DESCRIPTION = "Configuration file for kexecboot"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58"

PR = "r12"

SRC_URI = "file://icon.xpm"

CMDLINE ?= ""
CMDLINE_DEBUG ?= "${@base_conditional('DISTRO_TYPE', 'release', 'quiet', 'debug',d)}"

do_configure_prepend () {
    install -m 0644 ${WORKDIR}/icon.xpm ${S}
}

do_install_prepend () {
echo '# First kernel stanza.
# Specify full kernel path on target.
KERNEL=/boot/${KERNEL_IMAGETYPE}

# Show this label in kexecboot menu.
LABEL=${DISTRO}-${MACHINE}
#
# Append this tags to the kernel cmdline.
APPEND=${CMDLINE} ${CMDLINE_DEBUG}
#
# Specify optional initrd/initramfs.
# INITRD=/boot/initramfs.cpio.gz
#
# Specify full path for a custom icon for the menu-item.
# If not set, use device-icons as default (NAND, SD, CF, ...).
# ICON=/boot/icon.xpm
#
# Priority of item in kexecboot menu.
# Items with highest priority will be shown at top of menu.
# Default: 0 (lowest, ordered by device ordering)
# PRIORITY=10
#
#
# Second kernel stanza.
# KERNEL=/boot/${KERNEL_IMAGETYPE}-test
# LABEL=${DISTRO}-${MACHINE}-test
# APPEND=${CMDLINE}
#' >> ${S}/boot.cfg
}

do_install () {
        install -d ${D}/boot
        install -m 0644 boot.cfg ${D}/boot/boot.cfg
        install -m 0644 icon.xpm ${D}/boot/icon.xpm
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

FILES_${PN} += "/boot/*"
