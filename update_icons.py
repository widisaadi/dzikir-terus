import os
import sys
from PIL import Image

def main():
    logo_path = r"d:\create something\Mobile app\dzikir-terus\public\logo.png"
    if not os.path.exists(logo_path):
        print("Logo not found!")
        sys.exit(1)

    img = Image.open(logo_path).convert("RGBA")

    # Mipmap sizes
    sizes = {
        "mdpi": 48,
        "hdpi": 72,
        "xhdpi": 96,
        "xxhdpi": 144,
        "xxxhdpi": 192
    }

    base_res = r"d:\create something\Mobile app\dzikir-terus\app\src\main\res"

    for density, size in sizes.items():
        folder = os.path.join(base_res, f"mipmap-{density}")
        os.makedirs(folder, exist_ok=True)
        
        resized = img.resize((size, size), Image.Resampling.LANCZOS)
        
        # Save standard and round icon as png
        resized.save(os.path.join(folder, "ic_launcher.png"))
        resized.save(os.path.join(folder, "ic_launcher_round.png"))
        
        # Also save webp variants to overwrite the existing default ones
        resized.save(os.path.join(folder, "ic_launcher.webp"), "WEBP")
        resized.save(os.path.join(folder, "ic_launcher_round.webp"), "WEBP")

    # Foreground for adaptive icon (108x108)
    fg_size = 108
    fg_img = img.resize((fg_size, fg_size), Image.Resampling.LANCZOS)
    drawable_folder = os.path.join(base_res, "drawable")
    
    # We will just write ic_launcher_foreground.png to drawable, and change the xml 
    # to point to it, OR simply save as webp/png and update the v26 xml if needed.
    # Actually, Android supports PNG in drawable. 
    # But wait, existing is ic_launcher_foreground.xml. If we add .png, it might conflict.
    # So we should delete the existing .xml first.
    xml_fg = os.path.join(drawable_folder, "ic_launcher_foreground.xml")
    if os.path.exists(xml_fg):
        os.remove(xml_fg)
        
    fg_img.save(os.path.join(drawable_folder, "ic_launcher_foreground.png"))

    print("Icons updated successfully!")

if __name__ == "__main__":
    main()
