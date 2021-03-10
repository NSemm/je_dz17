package com.k7.services;

import com.k7.contacts.Contact;
import com.k7.contacts.ContactType;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@AllArgsConstructor
public class NioFileContactService implements ContactService {
    private String filePath;

    @Override
    public void add(Contact c) {
        try (SeekableByteChannel channel = Files.newByteChannel(
                Path.of(filePath),
                StandardOpenOption.APPEND)) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(new String(c.getName() + "[" + c.getType().toString().toLowerCase() + ":" + c.getValue() + "]\n")
                    .getBytes());
            buffer.flip();
            channel.write(buffer);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Integer id) {
        throw new UnsupportedOperationException("in develop");
    }

    @Override
    public List<Contact> findByName(String name) {
        return getContactList().stream()
                .filter(c -> c.getName().startsWith(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Contact> findByValue(String value) {
        return getContactList().stream()
                .filter(c -> c.getValue().startsWith(value))
                .collect(Collectors.toList());
    }

    @Override
    public List<Contact> getAll() {
        return getContactList();
    }

    private List<Contact> getContactList() {
        try (SeekableByteChannel channel = Files.newByteChannel(Path.of(filePath), StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            String in = "";
            List<Contact> contactList = new ArrayList<>();
            while (channel.read(buffer) != -1) {
                buffer.flip();
                in += getStringFromBuffer(buffer);
                String[] strParts = in.split("\n");
                for (int i = 0; i < strParts.length - 1; i++) {
                    // System.out.println("strParts[" + i + "]: " + strParts[i]);
                    contactList.add(parse(strParts[i]));
                }
                in = strParts[strParts.length - 1];
                // System.out.println("in: " + in);
                buffer.clear();
            }
            if (parse(in) != null)
                contactList.add(parse(in));
            return contactList;
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    private String getStringFromBuffer(ByteBuffer buffer) {
        return new String(buffer.array(), buffer.position(), buffer.limit());
    }

    private Contact parse(String user) {
        Pattern pattern = Pattern.compile("(?:(.+)\\[)(?:(.+):)(?:(.+)\\])");
        Matcher matcher = pattern.matcher(user);
        Contact parsedUser = new Contact();
            if ((!matcher.matches())) {
                System.out.println(user + " - Invalid user!");
                return null;
        } else {
            parsedUser.setName(matcher.group(1));
            parsedUser.setType(ContactType.valueOf(matcher.group(2).toUpperCase()));
            parsedUser.setValue(matcher.group(3));
        }
        return parsedUser;
    }
}