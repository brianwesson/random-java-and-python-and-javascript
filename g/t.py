import os
import shutil
import zipfile

file_system = {}

def create_directory(name):
    try:
        os.mkdir(name)
        print(f"Directory {name} created successfully")
    except FileExistsError:
        print(f"Directory {name} already exists")

def create_file(name, location, content=''):
    file_system[name] = {'location': location, 'content': content, 'metadata': {}}

def delete_file(name):
    del file_system[name]

def rename_file(name, new_name):
    file_system[new_name] = file_system.pop(name)

def move_file(name, current_location, new_location):
    file_system[name]['location'] = new_location

def copy_file(name, current_location, new_location):
    shutil.copyfile(os.path.join(current_location, name), os.path.join(new_location, name))

def move_directory(current_location, new_location):
    shutil.move(current_location, new_location)

def list_files(location, recursive=False):
    for name in file_system.keys():
        if recursive:
            if name.startswith(location):
                print(name)
        else:
            if os.path.dirname(name) == location:
                print(os.path.basename(name))

def set_permissions(name, permissions):
    file_system[name]['permissions'] = permissions

def check_permissions(name, user):
    if 'permissions' in file_system[name]:
        return user in file_system[name]['permissions']
    else:
        return True

def set_metadata(name, metadata):
    file_system[name]['metadata'] = metadata

def get_metadata(name):
    return file_system[name]['metadata']

def search_files(name=None, location=None, metadata=None):
    results = []
    for file_name, file_data in file_system.items():
        if name and not file_name.startswith(name):
            continue
        if location and not file_data['location'].startswith(location):
            continue
        if metadata and not all(item in file_data['metadata'].items() for item in metadata.items()):
            continue
        results.append(file_name)
    return results

def compress_files(files_to_compress, archive_name):
    with zipfile.ZipFile(archive_name, 'w', zipfile.ZIP_DEFLATED) as archive:
        for file_name in files_to_compress:
            archive.write(file_system[file_name]['location'], file_name)

def decompress_files(archive_name, extract_location):
    with zipfile.ZipFile(archive_name, 'r') as archive:
        archive.extractall(extract_location)
def main():
    while True:
        print("\nFile and Directory Management System")
        print("===================================")
        print("1. Create a file")
        print("2. Create a directory")
        print("3. Delete a file")
        print("4. Move a file")
        print("5. Copy a file")
        print("6. Move a directory")
        print("7. List files in directory")
        print("8. Set file permissions")
        print("9. Check file permissions")
        print("10. Set file metadata")
        print("11. Get file metadata")
        print("12. Search files")
        print("13. Compress files")
        print("14. Decompress files")
        print("0. Exit")

        choice = input("Enter your choice: ")

        if choice == '1':
            name = input("Enter file name: ")
            location = input("Enter location: ")
            try:
                create_file(name, location)
            except FileExistsError:
                print(f"File {name} already exists at {location}")
            except FileNotFoundError:
                print(f"Directory {location} not found")

        elif choice == '2':
            name = input("Enter directory name: ")
            location = input("Enter location: ")
            try:
                create_directory(name, location)
            except FileExistsError:
                print(f"Directory {name} already exists at {location}")
            except FileNotFoundError:
                print(f"Directory {location} not found")

        elif choice == '3':
            name = input("Enter file name: ")
            location = input("Enter location: ")
            try:
                delete_file(name, location)
            except FileNotFoundError:
                print(f"File {name} not found at {location}")

        elif choice == '4':
            name = input("Enter file name: ")
            current_location = input("Enter current location: ")
            new_location = input("Enter new location: ")
            try:
                move_file(name, current_location, new_location)
            except FileNotFoundError:
                print(f"File {name} not found at {current_location}")

        elif choice == '5':
            name = input("Enter file name: ")
            current_location = input("Enter current location: ")
            new_location = input("Enter new location: ")
            try:
                copy_file(name, current_location, new_location)
            except FileNotFoundError:
                print(f"File {name} not found at {current_location}")

        elif choice == '6':
            current_location = input("Enter current location: ")
            new_location = input("Enter new location: ")
            try:
                move_directory(current_location, new_location)
            except FileNotFoundError:
                print(f"Directory {current_location} not found")

        elif choice == '7':
            location = input("Enter location: ")
            recursive = input("Search recursively? (y/n) ") == 'y'
            try:
                list_files(location, recursive)
            except FileNotFoundError:
                print(f"Directory {location} not found")

        elif choice == '8':
            name = input("Enter file name: ")
            permissions = input("Enter permissions: ")
            try:
                set_permissions(name, permissions)
            except FileNotFoundError:
                print(f"File {name} not found")

        elif choice == '9':
            name = input("Enter file name: ")
            user = input("Enter user: ")
            try:
                allowed = check_permissions(name, user)
                print(f"{user} is allowed to access {name}: {allowed}")
            except FileNotFoundError:
                print(f"File {name} not found")
        elif choice == '10':
            name = input("Enter file name: ")
            metadata = input("Enter metadata (JSON format): ")
            try:
                set_metadata(name, metadata)
            except FileNotFoundError:
                print(f"File {name} not found")
            except ValueError:
                print("Invalid metadata format")

        elif choice == '11':
            name = input("Enter file name: ")
            try:
                metadata = get_metadata(name)
                print(metadata)
            except FileNotFoundError:
                print(f"File {name} not found")

        elif choice == '12':
            location = input("Enter search location: ")
            query = input("Enter search query: ")
            try:
                search_files(location, query)
            except FileNotFoundError:
                print(f"Directory {location} not found")

        elif choice == '13':
            files = input("Enter files (separated by commas): ")
            archive_name = input("Enter archive name: ")
            try:
                compress_files(files, archive_name)
            except FileNotFoundError as e:
                print(e)

        elif choice == '14':
            archive_name = input("Enter archive name: ")
            location = input("Enter decompression location: ")
            try:
                decompress_files(archive_name, location)
            except FileNotFoundError as e:
                print(e)

        elif choice == '0':
            print("Exiting...")
            break

        else:
            print("Invalid choice")

