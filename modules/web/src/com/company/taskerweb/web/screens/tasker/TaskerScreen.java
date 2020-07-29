package com.company.taskerweb.web.screens.tasker;

import com.company.taskerweb.entity.FileWork.LFile;
import com.company.taskerweb.entity.Tasks.TaskOne;
import com.company.taskerweb.entity.Tasks.TaskTwo;
import com.company.taskerweb.entity.Tasks.Tasks;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileLoader;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogOutcome;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;


import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;


@UiController("taskerweb_TaskerScreen")
@UiDescriptor("tasker-screen.xml")
public class TaskerScreen extends Screen {
    @Inject
    private TextArea<String> data;
    @Inject
    private LookupField<Tasks> combobox;
    @Inject
    private TextArea<String> answerArea;
    @Inject
    private Metadata metadata;
    @Inject
    private FileLoader fileLoader;
    @Inject
    private DataManager dataManager;
    @Inject
    private ExportDisplay exportDisplay;
    @Inject
    private Dialogs dialogs;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private FileUploadField upload;

    FileDescriptor fileDescriptor;

    //выбор выполняемой задачи
    @Subscribe("calc")
    public void onCalc(Action.ActionPerformedEvent event) {

        try {
            switch (combobox.getValue().getId()) {
                case ("Задача 1"): {
                    try {
                        setAnswerArea(doTaskOne(data.getRawValue()));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        dialogs.createMessageDialog()
                                .withCaption("Ошибка!")
                                .withMessage("Введены неверные данные!")
                                .show();
                    }
                    break;
                }
                case ("Задача 2"): {
                    try {
                        setAnswerArea(doTaskTwo(data.getRawValue()));
                    } catch (NumberFormatException e) {
                        dialogs.createMessageDialog().withCaption("Ошибка!")
                                .withMessage("Введены неверные данные!")
                                .show();
                    }
                    break;
                }

            }
        } catch (NullPointerException e) {
            dialogs.createMessageDialog().withCaption("Ошибка!")
                    .withMessage("Выберите задчу!")
                    .show();
        }
    }

    //действие при смене задачи в боксе
    @Subscribe("combobox")
    public void onComboboxValueChange(HasValue.ValueChangeEvent<Tasks> event) {
        data.clear();
        answerArea.clear();
    }

    //отображение id перечисления в меню
    @Install(to = "combobox", subject = "optionCaptionProvider")
    private String comboboxOptionCaptionProvider(Tasks tasks) {
        return tasks.getId();
    }

    @Subscribe("save")
    public void onSave(Action.ActionPerformedEvent event) throws FileStorageException {
        String str = combobox.getValue().getId() + ";" + data.getRawValue();
        byte[] bytes = str.getBytes();

        dialogs.createInputDialog(this)
                .withCaption("Введите имя файла")
                .withParameter(
                        InputParameter.stringParameter("Name")
                )
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(inputDialogCloseEvent -> {
                    if (inputDialogCloseEvent.closedWith(DialogOutcome.OK)) {
                        String name = inputDialogCloseEvent.getValue("Name");
                        saver(name, bytes);

                    }
                })
                .show();
    }

    private void setAnswerArea(String answer) {
        answerArea.setValue(answer);
    }

    private String doTaskOne(String data) {
        String[] arr = data.split("\n");
        String[] arr1 = arr[0].split(", ");
        String[] arr2 = arr[1].split(", ");

        TaskOne taskOne = new TaskOne(arr1, arr2);
        String result = "";
        for (String s : taskOne.calculate()) {
            if (result == "") {
                result = s;
            } else {
                result = result + ", " + s;
            }
        }
        return result;
    }

    private String doTaskTwo(String data) {

        return TaskTwo.expanded(Integer.parseInt(data));
    }

    private void saver(String name, byte[] bytes) {
        fileDescriptor = metadata.create(FileDescriptor.class);
        fileDescriptor.setName(name + ".txt");
        fileDescriptor.setExtension("txt");
        fileDescriptor.setSize((long) bytes.length);
        fileDescriptor.setCreateDate(new Date());

        try {
            fileLoader.saveStream(fileDescriptor, () -> new ByteArrayInputStream(bytes));
        } catch (FileStorageException e) {
            throw new RuntimeException(e);
        }
        dataManager.commit(fileDescriptor);

        exportDisplay.show(fileDescriptor, ExportFormat.TEXT);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored") //file.delete();
    @Subscribe("upload")
    public void onUploadFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) throws FileNotFoundException {

        File file = fileUploadingAPI.getFile(upload.getFileId());
        if (file != null) {
            try {
                answerArea.clear();

                String[] taskData = LFile.load(file).split(";");
                setCombobox(taskData[0]);
                setData(taskData[1]);
                //удаление файла с сервера
                file.delete();

            } catch (ArrayIndexOutOfBoundsException e) {
                dialogs.createMessageDialog().withCaption("Ошибка!")
                        .withMessage("Выберите другой файл!")
                        .show();
            }

        }


    }

    @Subscribe("upload")
    public void onUploadFileUploadError(UploadField.FileUploadErrorEvent event) {
        dialogs.createMessageDialog().withCaption("Ошибка!")
                .withMessage("Ошибка загрузки файла!")
                .show();

    }

    public void setData(String value) {
        data.setValue(value);

    }

    //смена значения бокса при загрузке данных из файла
    public void setCombobox(String task) {
        switch (task) {
            case ("Задача 1"):
                combobox.setValue(Tasks.TASK_1);
                break;
            case ("Задача 2"):
                combobox.setValue(Tasks.TASK_2);
                break;

        }
    }
}

