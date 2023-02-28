import os
import json
import tarfile
import tkinter as tk
from tkinter import filedialog


class FileManagerGUI:
    def __init__(self):
        self.window = tk.Tk()
        self.window.title("File Manager")
        
        self.location = tk.StringVar(value=".")
        self.filename = tk.StringVar(value="")
        self.permissions = tk.StringVar(value="644")
        self.metadata = tk.StringVar(value="{}")
        self.query = tk.StringVar(value="")
        self.archive_name = tk.StringVar(value="archive.tar.gz")
        
        # Create widgets
        self.location_label = tk.Label(self.window, text="Location:")
        self.location_entry = tk.Entry(self.window, textvariable=self.location)
        self.location_browse_button = tk.Button(self.window, text="Browse...", command=self.browse_location)
        self.location_list_button = tk.Button(self.window, text="List", command=self.list_location)
        
        self.filename_label = tk.Label(self.window, text="Filename:")
        self.filename_entry = tk.Entry(self.window, textvariable=self.filename)
        self.file_create_button = tk.Button(self.window, text="Create", command=self.create_file)
        self.file_delete_button = tk.Button(self.window, text="Delete", command=self.delete_file)
        
        self.dir_create_button = tk.Button(self.window, text="Create directory", command=self.create_directory)
        self.dir_delete_button = tk.Button(self.window, text="Delete directory", command=self.delete_directory)
        
        self.permissions_label = tk.Label(self.window, text="Permissions:")
        self.permissions_entry = tk.Entry(self.window, textvariable=self.permissions)
        self.set_permissions_button = tk.Button(self.window, text="Set permissions", command=self.set_permissions)
        
        self.metadata_label = tk.Label(self.window, text="Metadata:")
        self.metadata_entry = tk.Entry(self.window, textvariable=self.metadata)
        self.set_metadata_button = tk.Button(self.window, text="Set metadata", command=self.set_metadata)
        self.get_metadata_button = tk.Button(self.window, text="Get metadata", command=self.get_metadata)
        
        self.query_label = tk.Label(self.window, text="Search query:")
        self.query_entry = tk.Entry(self.window, textvariable=self.query)
        self.search_button = tk.Button(self.window, text="Search", command=self.search_files)
        
        self.archive_name_label = tk.Label(self.window, text="Archive name:")
        self.archive_name_entry = tk.Entry(self.window, textvariable=self.archive_name)
        self.compress_button = tk.Button(self.window, text="Compress", command=self.compress_files)
        
        # Layout widgets
        self.location_label.grid(row=0, column=0, sticky="w")
        self.location_entry.grid(row=0, column=1, padx=5, pady=5, sticky="we")
        self.location_browse_button.grid(row=0, column=2, padx=5, pady=5)
        self.location_list_button.grid(row=0, column=3, padx=5, pady=5)
        
        self.filename_label.grid(row=1, column=0, sticky="w")
        self.filename_entry.grid(row=1, column=1, padx=5, pady=5, sticky="we")
        self.file_create_button.grid(row=1, column=2, padx=5, pady=5)
        self.file_delete_button.grid(row=1, column=3, padx=5, pady=5)
        
        self.dir_create_button.grid(row=2, column=0, padx=5, pady=5)
        self.dir_delete_button.grid(row=2, column=1, padx=5, pady=5)
