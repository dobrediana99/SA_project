import re

VERSION_FILE = "version.txt"

def get_next_version():
    try:
        with open(VERSION_FILE, "r") as file:
            version = file.read().strip()
    except FileNotFoundError:
        version = "v1"

    match = re.match(r"v(\d+)", version)
    if match:
        next_version = f"v{int(match.group(1)) + 1}"
    else:
        next_version = "v1"

    with open(VERSION_FILE, "w") as file:
        file.write(next_version)

    return next_version

if __name__ == "__main__":
    print(get_next_version())
