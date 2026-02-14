# Install STS Spring tool suite

### Install
```
sudo mv spring-tools-for-eclipse-4.30.0.RELEASE-e4.35.0-linux.gtk.x86_64.tar.gz /opt/
cd /opt/
sudo tar -xvf spring-tools-for-eclipse-4.30.0.RELEASE-e4.35.0-linux.gtk.x86_64.tar.gz
cp sts-4.32.1.RELEASE /opt/sts-4.30.0.RELEASE
cd sts-4.30.0.RELEASE/
sudo nano /usr/share/applications/STS.desktop
```

### Shorcut
```
[Desktop Entry]
Name=STST 4.30.0
Comment=SSTST 4.30.0
Exec=/opt/sts-4.30.0.RELEASE/SpringToolSuite4
Icon=/opt/sts-4.30.0.RELEASE/icon.xpm
StartupNotify=true
Terminal=false
Type=Application
Categories=Development;IDE;
```
