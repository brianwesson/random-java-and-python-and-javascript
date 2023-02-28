#include <QApplication>
#include <QWidget>
#include <QLabel>
#include <QLineEdit>
#include <QPushButton>
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QMessageBox>
#include <QDir>
#include <QFileInfo>

class DirectoryManager : public QWidget {
public:
    DirectoryManager(QWidget* parent = nullptr) : QWidget(parent) {
        // Create the UI elements
        QLabel* nameLabel = new QLabel("Name:");
        QLabel* typeLabel = new QLabel("Type:");
        nameEdit = new QLineEdit;
        typeEdit = new QLineEdit;
        QPushButton* createButton = new QPushButton("Create");
        QPushButton* deleteButton = new QPushButton("Delete");

        // Connect the button signals to the appropriate slots
        connect(createButton, &QPushButton::clicked, this, &DirectoryManager::createItem);
        connect(deleteButton, &QPushButton::clicked, this, &DirectoryManager::deleteItem);

        // Create the layout for the UI
        QHBoxLayout* nameLayout = new QHBoxLayout;
        nameLayout->addWidget(nameLabel);
        nameLayout->addWidget(nameEdit);

        QHBoxLayout* typeLayout = new QHBoxLayout;
        typeLayout->addWidget(typeLabel);
        typeLayout->addWidget(typeEdit);

        QHBoxLayout* buttonLayout = new QHBoxLayout;
        buttonLayout->addWidget(createButton);
        buttonLayout->addWidget(deleteButton);

        QVBoxLayout* mainLayout = new QVBoxLayout;
        mainLayout->addLayout(nameLayout);
        mainLayout->addLayout(typeLayout);
        mainLayout->addLayout(buttonLayout);

        setLayout(mainLayout);
    }

private:
    QLineEdit* nameEdit;
    QLineEdit* typeEdit;

    void createItem() {
        QString name = nameEdit->text();
        QString type = typeEdit->text();

        if (type == "Directory") {
            QDir dir(name);
            if (!dir.exists()) {
                if (!dir.mkdir(".")) {
                    QMessageBox::critical(this, "Error", "Failed to create directory");
                    return;
                }
            } else {
                QMessageBox::critical(this, "Error", "Directory already exists");
                return;
            }
        } else if (type == "File") {
            QFile file(name);
            if (!file.exists()) {
                if (!file.open(QIODevice::WriteOnly)) {
                    QMessageBox::critical(this, "Error", "Failed to create file");
                    return;
                }
                file.close();
            } else {
                QMessageBox::critical(this, "Error", "File already exists");
                return;
            }
        } else {
            QMessageBox::critical(this, "Error", "Invalid type");
            return;
        }

        QMessageBox::information(this, "Success", "Item created successfully");
    }

    void deleteItem() {
        QString name = nameEdit->text();
        QString type = typeEdit->text();

        if (type == "Directory") {
            QDir dir(name);
            if (dir.exists()) {
                if (!dir.removeRecursively()) {
                    QMessageBox::critical(this, "Error", "Failed to delete directory");
                    return;
                }
            } else {
                QMessageBox::critical(this, "Error", "Directory does not exist");
                return;
            }
        } else if (type == "File") {
            QFile file(name);
            if (file.exists()) {
                if (!file.remove()) {
                    QMessageBox::critical(this, "Error", "Failed to delete file");
                    return;
                }
            } else {
                QMessageBox::critical(this, "Error", "File does not exist");
                    return;
        }
    } else {
        QMessageBox::critical(this, "Error", "Invalid type");
        return;
    }

    QMessageBox::information(this, "Success", "Item deleted successfully");
}
};
int main(int argc, char** argv) {
QApplication app(argc, argv);
Copy code
DirectoryManager manager;
manager.show();

return app.exec();
               
