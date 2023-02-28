public class Main {
    public static void main(String[] args) {
        // create a virtual machine
        VirtualMachine vm = new VirtualMachine("ubuntu-vm", "2", "2048", "20", "ubuntu-20.04.3-desktop-amd64.iso");
        vm.create();

        // create a virtual network interface
        VirtualNetworkInterface vni = new VirtualNetworkInterface("virtual-eth0", "00:11:22:33:44:55", "192.168.1.2");
        vni.create();

        // start the virtual machine
        vm.start();

        // connect to the virtual machine using SSH
        SSH ssh = new SSH("192.168.1.2", "ubuntu", "password");
        ssh.connect();

        // execute commands on the virtual machine
        ssh.executeCommand("sudo apt update");
        ssh.executeCommand("sudo apt install -y nginx");

        // disconnect from the virtual machine
        ssh.disconnect();

        // stop the virtual machine
        vm.stop();

        // destroy the virtual machine and virtual network interface
        vm.destroy();
        vni.destroy();
    }
}
