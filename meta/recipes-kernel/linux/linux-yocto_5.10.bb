KBRANCH ?= "v5.10/standard/base"

require recipes-kernel/linux/linux-yocto.inc

# board specific branches
KBRANCH_qemuarm  ?= "v5.10/standard/arm-versatile-926ejs"
KBRANCH_qemuarm64 ?= "v5.10/standard/qemuarm64"
KBRANCH_qemumips ?= "v5.10/standard/mti-malta32"
KBRANCH_qemuppc  ?= "v5.10/standard/qemuppc"
KBRANCH_qemuriscv64  ?= "v5.10/standard/base"
KBRANCH_qemuriscv32  ?= "v5.10/standard/base"
KBRANCH_qemux86  ?= "v5.10/standard/base"
KBRANCH_qemux86-64 ?= "v5.10/standard/base"
KBRANCH_qemumips64 ?= "v5.10/standard/mti-malta64"

SRCREV_machine_qemuarm ?= "0da8b6daad325e8c6ae957d9023e91186645a4cd"
SRCREV_machine_qemuarm64 ?= "2105ce2526ddd17827c23e89d6b4e9010261c08c"
SRCREV_machine_qemumips ?= "4b377a8a37f03f34fff1e1fcf49e31772e6fda08"
SRCREV_machine_qemuppc ?= "2105ce2526ddd17827c23e89d6b4e9010261c08c"
SRCREV_machine_qemuriscv64 ?= "2105ce2526ddd17827c23e89d6b4e9010261c08c"
SRCREV_machine_qemuriscv32 ?= "2105ce2526ddd17827c23e89d6b4e9010261c08c"
SRCREV_machine_qemux86 ?= "2105ce2526ddd17827c23e89d6b4e9010261c08c"
SRCREV_machine_qemux86-64 ?= "2105ce2526ddd17827c23e89d6b4e9010261c08c"
SRCREV_machine_qemumips64 ?= "df5eac625c02c6be577dbb1a7cc0472b71c704b4"
SRCREV_machine ?= "2105ce2526ddd17827c23e89d6b4e9010261c08c"
SRCREV_meta ?= "782d26ece99cac1f02ffc639097c16c3b3bab44a"

# remap qemuarm to qemuarma15 for the 5.8 kernel
# KMACHINE_qemuarm ?= "qemuarma15"

SRC_URI = "git://git.yoctoproject.org/linux-yocto.git;name=machine;branch=${KBRANCH}; \
           git://github.com/3mdeb/yocto-kernel-cache;type=kmeta;name=meta;branch=qemuppc64be;destsuffix=${KMETA}"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION ?= "5.10.36"

DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"
DEPENDS += "gmp-native"

PV = "${LINUX_VERSION}+git${SRCPV}"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"

KERNEL_DEVICETREE_qemuarmv5 = "versatile-pb.dtb"

COMPATIBLE_MACHINE = "qemuarm|qemuarmv5|qemuarm64|qemux86|qemuppc|qemuppc64|qemupc64be|qemumips|qemumips64|qemux86-64|qemuriscv64|qemuriscv32"

# Functionality flags
KERNEL_EXTRA_FEATURES ?= "features/netfilter/netfilter.scc"
KERNEL_FEATURES_append = " ${KERNEL_EXTRA_FEATURES}"
KERNEL_FEATURES_append_qemuall=" cfg/virtio.scc features/drm-bochs/drm-bochs.scc"
KERNEL_FEATURES_append_qemux86=" cfg/sound.scc cfg/paravirt_kvm.scc"
KERNEL_FEATURES_append_qemux86-64=" cfg/sound.scc cfg/paravirt_kvm.scc"
KERNEL_FEATURES_append = " ${@bb.utils.contains("TUNE_FEATURES", "mx32", " cfg/x32.scc", "", d)}"
KERNEL_FEATURES_append = " ${@bb.utils.contains("DISTRO_FEATURES", "ptest", " features/scsi/scsi-debug.scc", "", d)}"
KERNEL_FEATURES_append = " ${@bb.utils.contains("DISTRO_FEATURES", "ptest", " features/gpio/mockup.scc", "", d)}"
